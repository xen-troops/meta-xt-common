require xen-4.16-dunfell.inc

do_configure_append() {
    oe_runmake xt_defconfig
}
