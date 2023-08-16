SUMMARY = "Service for the weston 'up and ready' notification"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://weston-notification.service \
"
S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "weston-notification.service"

RDEPENDS:${PN} += " \
    xen-tools-xenstore \
"

FILES:${PN} = " \
    ${systemd_system_unitdir}/weston-notification.service \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/weston-notification.service ${D}${systemd_system_unitdir}
}
