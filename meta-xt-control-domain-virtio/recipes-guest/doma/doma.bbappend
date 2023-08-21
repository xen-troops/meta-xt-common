FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://virtio-env.conf \
"

RDEPENDS:${PN} += " \
    launch-domain \
"

FILES:${PN} += " \
    ${sysconfdir}/systemd/system/doma.service.d/virtio-env.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/doma.service.d
    install -m 0644 ${WORKDIR}/virtio-env.conf ${D}${sysconfdir}/systemd/system/doma.service.d
}
