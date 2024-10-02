FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# virtio-env-weston.conf whould be used if we have the weston compositor in domd
# the product should redefine variable.

XT_DOMD_DISPLAY_SYSTEM ??= "weston"

SRC_URI += "\
    file://virtio-env.conf \
    file://virtio-env-weston.conf \
    file://virtio-env-no-weston.conf \
"

RDEPENDS:${PN} += " \
    launch-domain \
"

FILES:${PN} += " \
    ${sysconfdir}/systemd/system/domu.service.d/virtio-env.conf \
    ${@bb.utils.contains('XT_DOMD_DISPLAY_SYSTEM', 'weston', \
        '${sysconfdir}/systemd/system/domu.service.d/virtio-env-weston.conf', \
        '${sysconfdir}/systemd/system/domu.service.d/virtio-env-no-weston.conf', d)} \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/domu.service.d
    install -m 0644 ${WORKDIR}/virtio-env.conf ${D}${sysconfdir}/systemd/system/domu.service.d

    if ${@bb.utils.contains('XT_DOMD_DISPLAY_SYSTEM', 'weston', 'true', 'false', d)}; then
        install -m 0644 ${WORKDIR}/virtio-env-weston.conf ${D}${sysconfdir}/systemd/system/domu.service.d
    else
        install -m 0644 ${WORKDIR}/virtio-env-no-weston.conf ${D}${sysconfdir}/systemd/system/domu.service.d
    fi
}
