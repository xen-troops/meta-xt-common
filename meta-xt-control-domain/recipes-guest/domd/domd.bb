SUMMARY = "Set of files to run a Driver domain"
DESCRIPTION = "A config file, kernel, dtb and scripts for a Driver domain"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit externalsrc systemd

EXTERNALSRC_SYMLINKS = ""

SRC_URI = "\
    file://${XT_DOMD_CONFIG_NAME} \
    file://domd.service \
"

FILES:${PN} = " \
    ${sysconfdir}/xen/domd.cfg \
    ${libdir}/xen/boot/domd.dtb \
    ${libdir}/xen/boot/linux-domd \
    ${systemd_unitdir}/system/domd.service \
"

SYSTEMD_SERVICE:${PN} = "domd.service"

do_install() {
    install -d ${D}${sysconfdir}/xen
    install -d ${D}${libdir}/xen/boot
    install -m 0644 ${WORKDIR}/${XT_DOMD_CONFIG_NAME} ${D}${sysconfdir}/xen/domd.cfg
    install -m 0644 ${S}/${XT_DOMD_DTB_NAME} ${D}${libdir}/xen/boot/domd.dtb
    install -m 0644 ${S}/Image ${D}${libdir}/xen/boot/linux-domd

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/domd.service ${D}${systemd_unitdir}/system/
}
