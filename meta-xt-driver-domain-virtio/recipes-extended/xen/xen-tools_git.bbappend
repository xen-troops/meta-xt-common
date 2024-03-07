FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

FILES:${PN}-devd += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${sysconfdir}/systemd/system/xendriverdomain.service.d/weston-env.conf', '', d)} \
"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
        install -m 0644 ${WORKDIR}/weston-env.conf ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
    fi
}

SRC_URI += "\
    file://weston-env.conf \
"
