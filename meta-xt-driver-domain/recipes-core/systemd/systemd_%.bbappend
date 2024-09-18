FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://ip_forward.conf \
    file://sdl-env-wayland.conf \
    file://sdl-env-kmsdrm.conf \
"

PACKAGECONFIG:append = " networkd"
PACKAGECONFIG:append = " iptc"
PACKAGECONFIG:append = " resolved"

USERADD_ERROR_DYNAMIC = "warn"

do_install:append() {
    install -m 0644 ${WORKDIR}/ip_forward.conf ${D}${sysconfdir}/tmpfiles.d/
    install -d ${D}${sysconfdir}/systemd/system.conf.d/
    # Check if 'weston' is included in the DISTRO_FEATURES or PACKAGECONFIG
    if ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'true', 'false', d)}; then
        install -m 0644 ${WORKDIR}/sdl-env-wayland.conf ${D}${sysconfdir}/systemd/system.conf.d/sdl-env.conf
    else
        install -m 0644 ${WORKDIR}/sdl-env-kmsdrm.conf ${D}${sysconfdir}/systemd/system.conf.d/sdl-env.conf
    fi
}
