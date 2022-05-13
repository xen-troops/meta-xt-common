DESCRIPTION = "Xen PV Display backend"
SECTION = "extras"
LICENSE = "GPLv2"
PR = "r0"

S = "${WORKDIR}/git"

inherit pkgconfig cmake  systemd

SYSTEMD_SERVICE_${PN} = "displbe.service"

SRC_URI = " \
    git://github.com/xen-troops/displ_be.git;protocol=https;branch=yocto-v4.7.0-xt0.1 \
    file://displbe.service \
    file://0001-zero-copy-Introduce-config-parameters-to-control-zco.patch \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

SRCREV = "${AUTOREV}"

DEPENDS = "libxenbe libconfig libdrm wayland git-native wayland-ivi-extension wayland-native"

EXTRA_OECMAKE = " -DWITH_DOC=OFF -DWITH_DRM=ON -DWITH_ZCOPY=ON -DWITH_WAYLAND=ON -DWITH_IVI_EXTENSION=ON -DWITH_INPUT=ON"

FILES_${PN} += " \
    ${systemd_system_unitdir}/displbe.service \
"

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/displbe.service ${D}${systemd_system_unitdir}
}

