FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://depend.conf \
"

FILES_${PN} += " \
    ${sysconfdir}/systemd/system/dnsmasq.service.d/depend.conf \
"

python () {
    if 'domu' in d.getVar('XT_GUEST_INSTALL', True).split():
        for pair in d.getVar('XT_GUEST_NETWORK_DOMU', True).split(';'):
            key, value = pair.split('=')
            if key == 'mac':
                d.setVar('DOMU_NET_MAC', value)
            if key == 'ip':
                d.setVar('DOMU_NET_IP', value)
    if 'doma' in d.getVar('XT_GUEST_INSTALL', True).split():
        for pair in d.getVar('XT_GUEST_NETWORK_DOMA', True).split(';'):
            key, value = pair.split('=')
            if key == 'mac':
                d.setVar('DOMA_NET_MAC', value)
            if key == 'ip':
                d.setVar('DOMA_NET_IP', value)
}

do_install_append() {
    # Make dnsmasq listen only on bridge interface
    echo "interface=xenbr0" >> ${D}${sysconfdir}/dnsmasq.conf

    # Define DHCP leases range. Upper part of subnet can be used
    # for static configuration.
    echo "dhcp-range=192.168.0.2,192.168.0.150,12h" >> ${D}${sysconfdir}/dnsmasq.conf

    # Configure IP addresses for DomA, DomU.
    # MAC addresses are defined in /etc/xen/domX.cfg
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'doma', 'true', 'false', d)}; then
        echo "dhcp-host="${DOMA_NET_MAC}",domu,"${DOMA_NET_IP}",infinite" >> ${D}${sysconfdir}/dnsmasq.conf
    fi
    if ${@bb.utils.contains('XT_GUEST_INSTALL', 'domu', 'true', 'false', d)}; then
        echo "dhcp-host="${DOMU_NET_MAC}",domu,"${DOMU_NET_IP}",infinite" >> ${D}${sysconfdir}/dnsmasq.conf
    fi

    # Use resolve.conf provided by systemd-resolved
    echo "resolv-file=/run/systemd/resolve/resolv.conf" >> ${D}${sysconfdir}/dnsmasq.conf

    # Add actual dependencies
    install -d ${D}${sysconfdir}/systemd/system/dnsmasq.service.d
    install -m 0644 ${WORKDIR}/depend.conf ${D}${sysconfdir}/systemd/system/dnsmasq.service.d/
}
