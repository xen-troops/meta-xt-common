DESCRIPTION = "C++ bindings for dbus"
HOMEPAGE = "https://dbus-cxx.github.io/"
SECTION = "libs"
LICENSE = "GPLv3"
DEPENDS = "boost dbus libsigc++-2.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/dbus-cxx/${PN}.git;nobranch=1;tag=${PV};protocol=https"
S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

BBCLASSEXTEND = "native"
