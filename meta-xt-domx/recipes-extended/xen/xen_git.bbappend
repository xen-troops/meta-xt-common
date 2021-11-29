require xen-source.inc

do_configure_append() {
    oe_runmake xt_defconfig
}
