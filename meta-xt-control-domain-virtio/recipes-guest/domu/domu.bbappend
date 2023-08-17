FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

RDEPENDS:${PN} += "\
bash \
"

SRC_URI += "\
file://virtio-env.conf \
file://domu-ExecStart.sh \
"

FILES:${PN} += " \
${sysconfdir}/systemd/system/domu.service.d/virtio-env.conf \
${libdir}/xen/bin/domu-ExecStart.sh \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/domu.service.d
    install -m 0644 ${WORKDIR}/virtio-env.conf ${D}${sysconfdir}/systemd/system/domu.service.d

    install -d ${D}${libdir}/xen/bin
    install -m 0755 ${WORKDIR}/domu-ExecStart.sh ${D}${libdir}/xen/bin
}
