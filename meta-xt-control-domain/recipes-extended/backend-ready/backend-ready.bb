SUMMARY = "systemd service that waits for backend to became ready"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PM} = "xen-tools-xenstore"

inherit systemd

SRC_URI = "\
    file://wait-for-backend \
    file://backend-ready@.service \
"

FILES:${PN} = " \
    ${libdir}/xen/bin/wait-for-backend \
    ${systemd_unitdir}/system/backend-ready@.service \
"

SYSTEMD_SERVICE:${PN} = "backend-ready@.service"

do_install() {
    install -d ${D}${libdir}/xen/bin
    install -m 0755 ${WORKDIR}/wait-for-backend ${D}${libdir}/xen/bin/wait-for-backend

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/backend-ready@.service ${D}${systemd_unitdir}/system/
}
