FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
    # Disable the idle timeout, although this could be done by using
    # PACKAGECONFIG, but meta-renesas layer supplies its own weston.ini, so
    # original file located at poky/meta/recipes-graphics/wayland/ gets skipped.
    sed -i -e "/^\[core\]/a idle-time=0" ${D}${sysconfdir}/xdg/weston/weston.ini
}
