require xen-source.inc

RDEPENDS:${PN} += "${PN}-devd"

RDEPENDS:${PN}:remove = "${PN}-xendomains"

do_install:append() {
    # FIXME: this is to fix QA Issue with pygrub:
    # ... pygrub maximum shebang size exceeded, the maximum size is 128. [shebang-size]
    rm -f ${D}/${bindir}/pygrub
    rm -f ${D}/${libdir}/xen/bin/pygrub
}

