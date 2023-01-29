SUMMARY = "Set of files to run a Zephyr-based guest "
DESCRIPTION = "A config file and service for a guest domain with Zephyr OS"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit systemd

EXTERNALSRC_SYMLINKS = ""

SRC_URI = "\
    file://${XT_DOMZ_CONFIG_NAME} \
    file://domz.service \
"

FILES:${PN} = " \
    ${sysconfdir}/xen/domz.cfg \
    ${systemd_unitdir}/system/domz.service \
    ${libdir}/xen/boot/zephyr-domz \
"

SYSTEMD_SERVICE:${PN} = "domz.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/xen
    install -m 0644 ${WORKDIR}/${XT_DOMZ_CONFIG_NAME} ${D}${sysconfdir}/xen/domz.cfg
    # EXTERNALSRC_pn_domz is provided in moulin.cfg
    install -d ${D}${libdir}/xen/boot
    install -m 0644 ${EXTERNALSRC}/zephyr.bin ${D}${libdir}/xen/boot/zephyr-domz

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/domz.service ${D}${systemd_unitdir}/system/
}
