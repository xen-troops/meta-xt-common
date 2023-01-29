SUMMARY = "Internal virtual network (guest side)"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://eth0.network \
    file://systemd-networkd-wait-online.conf \
"

S = "${WORKDIR}"

FILES:${PN} = " \
    ${sysconfdir}/systemd/network/eth0.network \
    ${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d/systemd-networkd-wait-online.conf \
"

RDEPENDS:${PN} = "systemd"

do_install() {
    install -d ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${S}/eth0.network ${D}${sysconfdir}/systemd/network

    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
    install -m 0644 ${S}/systemd-networkd-wait-online.conf \
		${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d
}
