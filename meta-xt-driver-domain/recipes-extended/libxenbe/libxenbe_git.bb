DESCRIPTION = "libxenbe"
SECTION = "libs"
LICENSE = "GPLv2"
PR = "r0"

SRC_URI = "git://github.com/xen-troops/libxenbe.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

DEPENDS = "xen-tools"
RDEPENDS_${PN} = " \
	 xen-tools-libxenstore \
	 xen-tools-libxenevtchn \
	 xen-tools-libxengnttab \
	 xen-tools-libxenctrl\
	 "

LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

S = "${WORKDIR}/git"

# Followig two lines are workaround to fix issue reflected as
# "QA Issue: -dev package contains non-symlink .so:"
# TODO: the proper solution should be analyzed in future
FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"

inherit pkgconfig cmake
