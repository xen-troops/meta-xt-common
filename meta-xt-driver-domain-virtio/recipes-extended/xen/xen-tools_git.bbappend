FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

FILES:${PN}-devd += " \
${sysconfdir}/systemd/system/xendriverdomain.service.d/weston-env.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
    install -m 0644 ${WORKDIR}/weston-env.conf ${D}${sysconfdir}/systemd/system/xendriverdomain.service.d
}

python () {
    src_uri_append = "file://weston-env.conf"
    if src_uri_append not in d.getVar('SRC_URI'):
        d.appendVar('SRC_URI', " " + src_uri_append)
}
