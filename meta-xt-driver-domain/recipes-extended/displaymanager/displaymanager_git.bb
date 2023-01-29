DESCRIPTION = "displaymanager"
SECTION = "extras"
LICENSE = "GPL-2.0-only"
PR = "r0"

inherit pkgconfig cmake systemd

SYSTEMD_SERVICE:${PN} = "display-manager.service"

DEPENDS = "libconfig wayland-ivi-extension glib-2.0 glib-2.0-native git-native xt-log"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = " \
    git://github.com/xen-troops/DisplayManager.git;protocol=https;branch=master \
    file://display_manager.conf \
    file://display-manager.service \
"
S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[doc] = "-DWITH_DOC=ON,-DWITH_DOC=OFF,doxygen-native"

EXTRA_OECMAKE:append = " -DCMAKE_BUILD_TYPE=Release"

do_install:append() {
    install -d ${D}${sysconfdir}/dbus-1/system.d
    install -m 0755 ${WORKDIR}/display_manager.conf ${D}${sysconfdir}/dbus-1/system.d/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/display-manager.service ${D}${systemd_system_unitdir}

    install -m 0744 ${WORKDIR}/${DM_CONFIG} ${D}${sysconfdir}/display_manager.cfg
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/display-manager.service \
    ${sysconfdir}/display_manager.cfg \
    ${sysconfdir}/dbus-1/session.d/display_manager.conf \
"

