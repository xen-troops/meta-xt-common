LIC_FILES_CHKSUM = "file://COPYING;md5=419739e325a50f3d7b4501338e44a4e5"

XEN_URL ??= "git://github.com/xen-troops/xen.git;protocol=https;branch=xen-4.16rc-migration"
XEN_REV ??= "81ac624282e6ab429dfdb858da8360faf45ca8d7"

SRC_URI = "${XEN_URL}"
XEN_REL = "4.16"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${XEN_REV}"

