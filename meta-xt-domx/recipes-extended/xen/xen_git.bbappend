require xen-source.inc

do_configure:append() {
    oe_runmake xt_defconfig
}
