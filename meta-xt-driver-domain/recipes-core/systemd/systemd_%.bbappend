FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://ip_forward.conf \
    file://sdl-env.conf \
"

PACKAGECONFIG:append = " networkd"
PACKAGECONFIG:append = " iptc"
PACKAGECONFIG:append = " resolved"

USERADD_ERROR_DYNAMIC = "warn"

do_install:append() {
    install -m 0644 ${UNPACKDIR}/ip_forward.conf ${D}${sysconfdir}/tmpfiles.d/
    install -d ${D}${sysconfdir}/systemd/system.conf.d/
    install -m 0644 ${UNPACKDIR}/sdl-env.conf ${D}${sysconfdir}/systemd/system.conf.d/
}
