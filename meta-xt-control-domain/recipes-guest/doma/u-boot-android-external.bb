SUMMARY = "Deployment of Android U-Boot, which is prebuilt externally."
DESCRIPTION = "U-Boot loader prebuilt externally to be installed to run Android-based guest"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit externalsrc

# externalsrc.bbclass creates symlinks oe-workdir and oe-logs to ${WORKDIR} and ${T}.
# Useful for access, but may interfere with version control or become broken.
# Set EXTERNALSRC_SYMLINKS = "" to disable them.
EXTERNALSRC_SYMLINKS = ""

FILES:${PN} = " \
                ${libdir}/xen/boot/u-boot-doma \
            "

do_install() {
    install -d ${D}/${libdir}/xen/boot/
    install -m 0644 ${S}/u-boot.bin ${D}/${libdir}/xen/boot/u-boot-doma
}
