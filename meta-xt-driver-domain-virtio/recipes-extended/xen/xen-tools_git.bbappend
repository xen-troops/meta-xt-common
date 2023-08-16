FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

FILES:${PN}-devd += " \
${sysconfdir}/systemd/system/xendriverdomain.service.d/weston-env.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
    install -m 0644 ${WORKDIR}/weston-env.conf ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
}

SRC_URI += "\
    file://weston-env.conf \
"
