DESCRIPTION = "Para-virtualized sound backend"
SECTION = "extras"
LICENSE = "GPLv2"
PR = "r0"

inherit pkgconfig cmake systemd

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'pulseaudio alsa', d)}"
PACKAGECONFIG[pulseaudio] = "-DWITH_PULSE=ON,-DWITH_PULSE=OFF,pulseaudio,pulseaudio-server"
PACKAGECONFIG[alsa] = "-DWITH_ALSA=ON,-DWITH_ALSA=OFF,alsa-lib,alsa-server"

DEPENDS = "libxenbe libconfig git-native"

RDEPENDS_${PN} = "libxenbe libconfig"

SRC_URI = " \
    git://github.com/xen-troops/snd_be.git;protocol=https;branch=yocto-v4.7.0-xt0.1 \
    file://sndbe.service \
"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"

EXTRA_OECMAKE = " -DWITH_DOC=OFF"

SYSTEMD_SERVICE_${PN} = "sndbe.service"

FILES_${PN} += " \
    ${systemd_system_unitdir}/sndbe.service \
"

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sndbe.service ${D}${systemd_system_unitdir}
}

