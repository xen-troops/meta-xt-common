DESCRIPTION = "Para-virtualized sound backend"
SECTION = "extras"
LICENSE = "GPL-2.0-only"
PR = "r0"

inherit pkgconfig cmake systemd

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'pulseaudio alsa', d)}"
PACKAGECONFIG[pulseaudio] = "-DWITH_PULSE=ON,-DWITH_PULSE=OFF,pulseaudio,pulseaudio-server"
PACKAGECONFIG[alsa] = "-DWITH_ALSA=ON,-DWITH_ALSA=OFF,alsa-lib,alsa-server"
PACKAGECONFIG[doc] = "-DWITH_DOC=ON,-DWITH_DOC=OFF,doxygen-native"

DEPENDS = "libxenbe libconfig git-native"

RDEPENDS:${PN} += " \
    libxenbe \
    libconfig \
    xen-tools-xenstore \
"

SRC_URI = " \
    git://github.com/xen-troops/snd_be.git;protocol=https;branch=master \
    file://sndbe.service \
"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

S = "${WORKDIR}/git"

SRCREV = "b2764c2849f02c051f1d16dc6b592da59d1675c1"

SYSTEMD_SERVICE:${PN} = "sndbe.service"

FILES:${PN} += " \
    ${systemd_system_unitdir}/sndbe.service \
"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sndbe.service ${D}${systemd_system_unitdir}
}

