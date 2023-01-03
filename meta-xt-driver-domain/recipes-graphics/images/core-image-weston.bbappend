IMAGE_INSTALL_append = " \
    xen \
    xen-tools-devd \
    xen-tools-scripts-network \
    xen-tools-scripts-block \
    xen-tools-xenstore \
    displbe \
    xen-network \
    dnsmasq \
    block \
    glmark2 \
    kmscube \
    ${@bb.utils.contains('DISTRO_FEATURES', 'ivi-shell', 'displaymanager', '', d)} \
"
