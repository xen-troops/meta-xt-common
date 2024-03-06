FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://virtio-env.conf \
    file://virtio-env-weston.conf \
"

RDEPENDS:${PN} += " \
    launch-domain \
"

FILES:${PN} += " \
    ${sysconfdir}/systemd/system/domu.service.d/virtio-env.conf \
    ${@bb.utils.contains('MACHINE_FEATURES', 'gsx', '${sysconfdir}/systemd/system/domu.service.d/virtio-env-weston.conf', '', d)} \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/domu.service.d
    install -m 0644 ${WORKDIR}/virtio-env.conf ${D}${sysconfdir}/systemd/system/domu.service.d

    # this virtio-env-weston.conf whould be used if we have the 'wayland'
    # distro feature inside DomU. But to avoid introducing new variable
    # we can look on presence of GSX on the board.
    if ${@bb.utils.contains('MACHINE_FEATURES', 'gsx', 'true', 'false', d)}; then
        install -m 0644 ${WORKDIR}/virtio-env-weston.conf ${D}${sysconfdir}/systemd/system/domu.service.d
    fi
}
