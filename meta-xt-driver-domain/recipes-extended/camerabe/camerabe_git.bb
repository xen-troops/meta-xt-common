DESCRIPTION = "Xen PV Camera backend"
SECTION = "extras"
LICENSE = "GPL-2.0-only"
PR = "r0"

inherit pkgconfig cmake systemd

DEPENDS = "libxenbe libconfig libv4l git-native"
RDEPENDS:${PN} = "libxenbe libconfig libv4l media-ctl v4l-utils"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

SYSTEMD_SERVICE:${PN} = "camerabe.service"

SRC_URI = " \
    git://github.com/xen-troops/camera_be.git;protocol=https;branch=master \
    file://camerabe.service \
    file://camera_be.cfg \
"

SRCREV = "${AUTOREV}"

RDEPENDS:${PN} += " \
    xen-tools-xenstore \
"

RRECOMMENDS:${PN} += " \
    virtual/xenstored \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[doc] = "-DWITH_DOC=ON,-DWITH_DOC=OFF,doxygen-native"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/camerabe.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/camera_be.cfg ${D}${sysconfdir}/camera_be.cfg
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/camerabe.service \
    ${sysconfdir}/camera_be.cfg \
"
