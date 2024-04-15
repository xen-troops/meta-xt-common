DESCRIPTION = "C++ bindings for dbus"
HOMEPAGE = "https://dbus-cxx.github.io/"
SECTION = "libs"
LICENSE = "GPLv3"
DEPENDS = "boost dbus libsigc++-2.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/dbus-cxx/${PN}.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"
# commit corresponds to tag '0.10.0'
SRCREV = "1d9425027860d5b9006178aefc2e638c48848477"

inherit autotools-brokensep pkgconfig

BBCLASSEXTEND = "native"
DEPENDS_virtclass-native = "dbus-native libsigc++-2.0-native"
