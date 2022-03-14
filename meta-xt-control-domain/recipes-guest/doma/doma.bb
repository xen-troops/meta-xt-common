SUMMARY = "Set of files to run an Android-based guest "
DESCRIPTION = "A config file, dtb and scripts for a guest domain"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit externalsrc systemd

# We use custom U-BOOT to run the Android
RDEPENDS_${PN} = "u-boot-android"

XT_DOMA_CONFIG_NAME ?= "doma-generic.cfg"

SRC_URI = "\
    file://${XT_DOMA_CONFIG_NAME} \
    file://doma.service \
"

FILES_${PN} = " \
    ${sysconfdir}/xen/doma.cfg \
    ${libdir}/xen/boot/doma.dtb \
    ${systemd_unitdir}/system/doma.service \
"

SYSTEMD_SERVICE_${PN} = "doma.service"

do_install() {
    install -d ${D}${sysconfdir}/xen
    install -d ${D}${libdir}/xen/boot
    install -m 0644 ${WORKDIR}/${XT_DOMA_CONFIG_NAME} ${D}${sysconfdir}/xen/doma.cfg
    install -m 0644 ${S}/${XT_DOMA_DTB_NAME} ${D}${libdir}/xen/boot/doma.dtb

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/doma.service ${D}${systemd_unitdir}/system/
}
