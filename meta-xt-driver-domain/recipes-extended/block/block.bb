SUMMARY = "Service for the block device up notification"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://block-up-notification.service \
"
S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE_${PN} = "block-up-notification.service"

RDEPDENDS_${PN} += " \
    xen-tools-xenstore \
"

RRECOMMENDS_${PN} += " \
    virtual/xenstored \
"

FILES_${PN} = " \
    ${systemd_system_unitdir}/block-up-notification.service \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/block-up-notification.service ${D}${systemd_system_unitdir}
}
