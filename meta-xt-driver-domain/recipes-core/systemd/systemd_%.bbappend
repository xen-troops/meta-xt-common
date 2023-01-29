FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://ip_forward.conf \
"

PACKAGECONFIG:append = " networkd"
PACKAGECONFIG:append = " iptc"
PACKAGECONFIG:append = " resolved"

USERADD_ERROR_DYNAMIC = "warn"

do_install:append() {
    install -m 0644 ${WORKDIR}/ip_forward.conf ${D}${sysconfdir}/tmpfiles.d/
}
