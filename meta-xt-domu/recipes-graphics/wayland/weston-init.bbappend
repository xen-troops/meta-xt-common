FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:enable_virtio = " \
    file://weston.sh \
"

FILES:${PN}:append:enable_virtio = " \
    ${sysconfdir}/profile.d/weston.sh \
"

do_install:append() {
    # Disable the idle timeout, although this could be done by using
    # PACKAGECONFIG, but meta-renesas layer supplies its own weston.ini, so
    # original file located at poky/meta/recipes-graphics/wayland/ gets skipped.
    sed -i -e "/^\[core\]/a idle-time=0" ${D}${sysconfdir}/xdg/weston/weston.ini

    if ${@bb.utils.contains('DISTRO_FEATURES', 'enable_virtio', 'true', 'false', d)}; then
        # For virtio set XDG_RUNTIME_DIR to /run/user/$UID (e.g. run/user/0)
        install -d ${D}/${sysconfdir}/profile.d
        install -m 0755 ${WORKDIR}/weston.sh ${D}/${sysconfdir}/profile.d/weston.sh
    fi
}
