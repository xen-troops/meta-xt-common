SUMMARY = "Internal virtual network"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://bridge-up-notification.service \
    file://eth0.network \
    file://xenbr0.netdev \
    file://xenbr0.network \
    file://xenbr0-systemd-networkd.conf \
    file://port-forward-systemd-networkd.conf \
    file://systemd-networkd-wait-online.conf \
"

S = "${WORKDIR}"

inherit systemd

FILES_${PN} = " \
    ${sysconfdir}/systemd/network/eth0.network \
    ${sysconfdir}/systemd/network/xenbr0.netdev \
    ${sysconfdir}/systemd/network/xenbr0.network \
    ${sysconfdir}/systemd/system/systemd-networkd.service.d/xenbr0-systemd-networkd.conf \
    ${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf \
    ${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d/systemd-networkd-wait-online.conf \
    ${systemd_system_unitdir}/bridge-up-notification.service \
"

RDEPENDS_${PN} = " \
    ethtool \
    iptables \
    kernel-module-xt-nat \
    kernel-module-xt-tcpudp \
    kernel-module-xt-masquerade \
"

python () {
    if 'domu' in d.getVar('XT_GUEST_INSTALL', True).split():
        for pair in d.getVar('XT_GUEST_NETWORK_DOMU', True).split(';'):
            key, value = pair.split('=')
            if key == 'ip':
                d.setVar('DOMU_NET_IP', value)

    if 'doma' in d.getVar('XT_GUEST_INSTALL', True).split():
        for pair in d.getVar('XT_GUEST_NETWORK_DOMA', True).split(';'):
            key, value = pair.split('=')
            if key == 'ip':
                d.setVar('DOMA_NET_IP', value)
}

do_install() {
    # Install bridge/network artifacts
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/bridge-up-notification.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${S}/*.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${S}/*.netdev ${D}${sysconfdir}/systemd/network

    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d
    install -m 0644 ${S}/xenbr0-systemd-networkd.conf ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d
    install -m 0644 ${S}/port-forward-systemd-networkd.conf ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'doma', 'true', 'false', d)}; then
        echo "\n# ADB to domA" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 5555 -j DNAT --to-destination "${DOMA_NET_IP}":5555" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
    fi
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'domu', 'true', 'false', d)}; then
        echo "\n# SSH to domU" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 2025 -j DNAT --to-destination "${DOMU_NET_IP}":22" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
    fi

    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
    install -m 0644 ${S}/systemd-networkd-wait-online.conf ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
}
