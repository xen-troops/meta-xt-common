SUMMARY = "Internal virtual network"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://bridge-up-notification.service \
    file://eth0.network \
    file://xenbr0.netdev \
    file://60-xenbr0.network \
    file://xenbr0-systemd-networkd.conf \
    file://port-forward-systemd-networkd.conf \
    file://systemd-networkd-wait-online.conf \
"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "bridge-up-notification.service"

FILES:${PN} = " \
    ${sysconfdir}/systemd/network/eth0.network \
    ${sysconfdir}/systemd/network/xenbr0.netdev \
    ${sysconfdir}/systemd/network/60-xenbr0.network \
    ${sysconfdir}/systemd/system/systemd-networkd.service.d/xenbr0-systemd-networkd.conf \
    ${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf \
    ${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d/systemd-networkd-wait-online.conf \
    ${systemd_system_unitdir}/bridge-up-notification.service \
"

RDEPENDS:${PN} = " \
    ethtool \
    iptables \
    kernel-module-xt-nat \
    kernel-module-xt-tcpudp \
    kernel-module-xt-masquerade \
    xen-tools-xenstore \
"

RRECOMMENDS:${PN} += " \
    virtual/xenstored \
"

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
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'domf', 'true', 'false', d)}; then
        echo "\n# SSH to domF" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 2022 -j DNAT --to-destination 192.168.0.3:22" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 8089 -j DNAT --to-destination 192.168.0.3:8089" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
    fi
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'doma', 'true', 'false', d)}; then
        echo "\n# ADB to domA" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 5555 -j DNAT --to-destination 192.168.0.4:5555" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
    fi
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'domu', 'true', 'false', d)}; then
        echo "\n# SSH to domU" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
        echo "ExecStartPost=+/usr/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 2025 -j DNAT --to-destination 192.168.0.5:22" \
            >> ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d/port-forward-systemd-networkd.conf
    fi

    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
    install -m 0644 ${S}/systemd-networkd-wait-online.conf ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
}
