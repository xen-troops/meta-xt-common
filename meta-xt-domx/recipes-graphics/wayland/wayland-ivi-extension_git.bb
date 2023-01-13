SUMMARY = "Wayland IVI Extension"
DESCRIPTION = "GENIVI Layer Management API based on Wayland IVI Extension"
HOMEPAGE = "https://github.com/COVESA/wayland-ivi-extension"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f1a56bb2dadf5f2be8eb342acf4ed79"

DEPENDS = "weston virtual/libgles2 pixman wayland-native"

SRC_URI = " \
    git://github.com/COVESA/${BPN}.git;protocol=https;branch=master \
    file://0001-ivi-input-controller-update-to-weston-7-header-files.patch  \
    file://0002-ivi-id-agent-update-to-weston-7-header-files.patch \
    file://0003-ivi-id-agent-update-dependencies-to-build-on-weston-.patch \
    file://0004-ivi-id-agent-update-dependencies-to-build-on-weston-.patch \
    file://0005-Fix-buld-error-in-krikston.patch \
    file://0006-Drop-weston-6-support-and-adjust-weston-7-8-10.patch \
    "
SRCREV = "f6911a11dc911a5bcb380d0895db6cfd533a3569"

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
