DESCRIPTION = "virtio-disk"
SECTION = "extras"
LICENSE = "GPL-2.0-only"
PR = "r0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

DEPENDS = "xen-tools"

SRCREV = "274f5e933a7381a9eb2a7b5856f37c3d982f3874"

SRC_URI:append = " \
    git://github.com/xen-troops/virtio-disk.git;protocol=https;branch=virtio_next \
    file://virtio-disk.service \
"

inherit systemd pkgconfig autotools-brokensep

SYSTEMD_SERVICE:${PN} = "virtio-disk.service"

RDEPENDS:${PN} += " \
    xen-tools-xenstore \
"

RRECOMMENDS:${PN} += " \
    virtual/xenstored \
"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/virtio-disk.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/virtio-disk.service \
"
