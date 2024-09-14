SUMMARY = "Wayland IVI Extension"
DESCRIPTION = "GENIVI Layer Management API based on Wayland IVI Extension"
HOMEPAGE = "https://github.com/COVESA/wayland-ivi-extension"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f1a56bb2dadf5f2be8eb342acf4ed79"

DEPENDS = "weston virtual/libgles2 pixman wayland-native"

SRC_URI = " \
    git://github.com/COVESA/wayland-ivi-extension.git;protocol=https;branch=master \
    file://0001-hack-use-weston-13.patch \
    "
SRCREV = "60d616ad3abd925956207b9b6ff981afa004e792"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE := "-DWITH_ILM_INPUT=1"
EXTRA_OECMAKE += "-DLIB_SUFFIX=${@d.getVar('baselib').replace('lib', '')}"

FILES:${PN} += "${datadir}/wayland-protocols/stable/ivi-application/ivi-application.xml"
FILES:${PN} += "${libdir}/weston/*"
FILES:${PN}-dbg += "${libdir}/weston/.debug/*"

# Need these temporarily to prevent a non-fatal do_package_qa issue
INSANE_SKIP:${PN} += "dev-deps"
INSANE_SKIP:${PN}-dev += "dev-elf dev-so"
