LIC_FILES_CHKSUM = "file://COPYING;md5=419739e325a50f3d7b4501338e44a4e5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

XEN_URL ??= "git://github.com/xen-troops/xen.git;protocol=https;branch=xen-4.16rc-migration"
XEN_REV ??= "${AUTOREV}"

SRC_URI = "${XEN_URL} \
	file://0001-smmu-Fix-guest-domain-restart.patch \
	"
XEN_REL = "4.16"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${XEN_REV}"

