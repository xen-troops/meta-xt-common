
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
    if echo "${DISTRO_FEATURES}" | grep -q "ivi-shell"; then
        sed -i '/repaint-window=*/c\repaint-window=8' \
            ${D}/${sysconfdir}/xdg/weston/weston.ini
    fi

    # Disable the idle timeout, although this could be done by using
    # PACKAGECONFIG, but meta-renesas layer supplies its own weston.ini, so
    # original file located at poky/meta/recipes-graphics/wayland/ gets skipped.
    sed -i -e "/^\[core\]/a idle-time=0" ${D}${sysconfdir}/xdg/weston/weston.ini
}
