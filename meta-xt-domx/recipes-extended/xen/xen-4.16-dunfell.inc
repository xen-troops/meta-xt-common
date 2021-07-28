LIC_FILES_CHKSUM = "file://COPYING;md5=419739e325a50f3d7b4501338e44a4e5"

SRC_URI = "git://github.com/xen-troops/xen.git;protocol=https;branch=xen-4.16rc-migration"
XEN_REL = "4.16"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

