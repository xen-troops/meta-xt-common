require xen-source.inc

RDEPENDS_${PN} += "${PN}-devd"

FILES_${PN}-xenhypfs = "\
    ${sbindir}/xenhypfs \
    "

FILES_${PN}-libxenhypfs = "${libdir}/libxenhypfs.so.*"
FILES_${PN}-libxenhypfs-dev = " \
    ${libdir}/libxenhypfs.so \
    ${libdir}/pkgconfig/xenhypfs.pc \
    "

FILES_${PN}-libxendevicemodel = "${libdir}/libxendevicemodel.so.*"
FILES_${PN}-libxendevicemodel-dev = " \
    ${libdir}/libxendevicemodel.so \
    ${libdir}/pkgconfig/xendevicemodel.pc \
    "

FILES_${PN}-misc_append = "\
    ${libdir}/xen/bin/depriv-fd-checker \
    ${bindir}/vchan-socket-proxy \
    "

FILES_${PN}-xl += "\
    ${sysconfdir}/bash_completion.d \
    "

FILES_${PN}-xl-examples += "\
    ${sysconfdir}/xen/xlexample.pvhlinux \
    "

FILES_${PN}-xen-access += "\
    ${sbindir}/xen-access \
    "

do_install_append() {
    # FIXME: this is to fix QA Issue with pygrub:
    # ... pygrub maximum shebang size exceeded, the maximum size is 128. [shebang-size]
    rm -f ${D}/${bindir}/pygrub
    rm -f ${D}/${libdir}/xen/bin/pygrub

    rm -f ${D}/${systemd_unitdir}/system/xen-qemu-dom0-disk-backend.service
}

FILES_${PN}-xencommons_remove = "\
    ${systemd_unitdir}/system/xen-qemu-dom0-disk-backend.service \
"

FILES_${PN}-tests += "\
    ${libdir}/xen/bin/test-xenstore \
    ${libdir}/xen/bin/test-resource \
"

SYSTEMD_SERVICE_${PN}-xencommons_remove = " \
    xen-qemu-dom0-disk-backend.service \
"

PACKAGES += "\
    ${PN}-tests \
"
