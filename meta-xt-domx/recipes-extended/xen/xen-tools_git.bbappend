require xen-source.inc

RDEPENDS:${PN} += "${PN}-devd"

RDEPENDS:${PN}:remove = "${PN}-xendomains"

PACKAGES:append = "\
    ${PN}-libxenhypfs \
    ${PN}-libxenhypfs-dev \
    ${PN}-xenhypfs \
    ${PN}-xen-access \
    "

FILES:${PN}-xenhypfs = "\
    ${sbindir}/xenhypfs \
    "

FILES:${PN}-libxenhypfs = "${libdir}/libxenhypfs.so.*"
FILES:${PN}-libxenhypfs-dev = " \
    ${libdir}/libxenhypfs.so \
    ${libdir}/pkgconfig/xenhypfs.pc \
    "

FILES:${PN}-libxendevicemodel = "${libdir}/libxendevicemodel.so.*"
FILES:${PN}-libxendevicemodel-dev = " \
    ${libdir}/libxendevicemodel.so \
    ${libdir}/pkgconfig/xendevicemodel.pc \
    "

FILES:${PN}-misc:append = "\
    ${libdir}/xen/bin/depriv-fd-checker \
    ${bindir}/vchan-socket-proxy \
    "

FILES:${PN}-xl += "\
    ${sysconfdir}/bash_completion.d \
    "

FILES:${PN}-xl-examples += "\
    ${sysconfdir}/xen/xlexample.pvhlinux \
    "

FILES:${PN}-xen-access += "\
    ${sbindir}/xen-access \
    "

do_install:append() {
    # FIXME: this is to fix QA Issue with pygrub:
    # ... pygrub maximum shebang size exceeded, the maximum size is 128. [shebang-size]
    rm -f ${D}/${bindir}/pygrub
    rm -f ${D}/${libdir}/xen/bin/pygrub

    rm -f ${D}/${systemd_unitdir}/system/xen-qemu-dom0-disk-backend.service
}

FILES:${PN}-xencommons:remove = "\
    ${systemd_unitdir}/system/xen-qemu-dom0-disk-backend.service \
"

SYSTEMD_SERVICE:${PN}-xencommons:remove = " \
    xen-qemu-dom0-disk-backend.service \
"
