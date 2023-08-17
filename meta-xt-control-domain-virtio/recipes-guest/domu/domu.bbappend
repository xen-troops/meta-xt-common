FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://virtio-env.conf \
"

RDEPENDS:${PN} += " \
    launch-domain \
"

FILES:${PN} += " \
    ${sysconfdir}/systemd/system/domu.service.d/virtio-env.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/domu.service.d
    install -m 0644 ${WORKDIR}/virtio-env.conf ${D}${sysconfdir}/systemd/system/domu.service.d
}
