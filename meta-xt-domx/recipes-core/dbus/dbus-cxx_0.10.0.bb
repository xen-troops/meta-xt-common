DESCRIPTION = "C++ bindings for dbus"
HOMEPAGE = "https://dbus-cxx.github.io/"
SECTION = "libs"
LICENSE = "GPLv3"
DEPENDS = "boost dbus libsigc++-2.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "https://github.com/dbus-cxx/${PN}.git;nobranch=1;tag=${PV}"
S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

BBCLASSEXTEND = "native"
DEPENDS_virtclass-native = "dbus-native libsigc++-2.0-native"
