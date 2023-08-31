SUMMARY = "This package delivers the script, which is used to \
launch the guest domains in case when we work with the virtio device sharing."

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PN} = "xen-tools-xenstore"

SRC_URI = "\
    file://launch-domain \
"

FILES:${PN} = " \
    ${libdir}/xen/bin/launch-domain \
"

do_install() {
    install -d ${D}${libdir}/xen/bin
    install -m 0755 ${WORKDIR}/launch-domain ${D}${libdir}/xen/bin/launch-domain
}
