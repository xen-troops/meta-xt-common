DESCRIPTION = "Xen PV Display backend"
SECTION = "extras"
LICENSE = "GPL-2.0-only"
PR = "r0"

S = "${WORKDIR}/git"

inherit pkgconfig cmake  systemd

SYSTEMD_SERVICE:${PN} = "displbe.service"

SRC_URI = " \
    git://github.com/xen-troops/displ_be.git;protocol=https;branch=master \
    file://displbe.service \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

SRCREV = "${AUTOREV}"

DEPENDS = "libxenbe libconfig git-native"

PACKAGECONFIG ??= "\
    drm \
    input \
    zcopy zcopy-drm zcopy-kms zcopy-dmabuf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland wayland-ivi','', d)} \
"

PACKAGECONFIG[doc] = "-DWITH_DOC=ON,-DWITH_DOC=OFF,doxygen-native"
PACKAGECONFIG[drm] = "-DWITH_DRM=ON,-DWITH_DRM=OFF,libdrm"
PACKAGECONFIG[input] = "-DWITH_INPUT=ON,-DWITH_INPUT=OFF,"
PACKAGECONFIG[wayland] = "-DWITH_WAYLAND=ON,-DWITH_WAYLAND=OFF,wayland wayland-native"
PACKAGECONFIG[wayland-ivi] = "-DWITH_WAYLAND=ON -DWITH_IVI_EXTENSION=ON,-DWITH_WAYLAND=OFF -DWITH_IVI_EXTENSION=OFF,wayland wayland-native wayland-ivi-extension"
PACKAGECONFIG[zcopy] = "-DWITH_ZCOPY=ON,-DWITH_ZCOPY=OFF,"
PACKAGECONFIG[zcopy-drm] = "-DWITH_DRM_ZCOPY=ON,-DWITH_DRM_ZCOPY=OFF,"
PACKAGECONFIG[zcopy-kms] = "-DWITH_KMS_ZCOPY=ON,-DWITH_KMS_ZCOPY=OFF,"
PACKAGECONFIG[zcopy-dmabuf] = "-DWITH_DMABUF_ZCOPY=ON,-DWITH_DMABUF_ZCOPY=OFF,"

RDEPENDS:${PN} += " \
    xen-tools-xenstore \
"

RRECOMMENDS:${PN} += " \
    virtual/xenstored \
"

FILES:${PN} += " \
    ${systemd_system_unitdir}/displbe.service \
"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/displbe.service ${D}${systemd_system_unitdir}
}

