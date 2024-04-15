DESCRIPTION = "C++ header only log"
SECTION = "libs"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/xen-troops/${BPN}.git;protocol=https;branch=master"
SRCREV = "a88163b7f64c4eec4f7362ba33ecd55bd3dd1cdb"

S = "${WORKDIR}/git"

inherit pkgconfig cmake
