FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://60-vif-emu.network \
"

FILES:${PN} += " \
    ${sysconfdir}/systemd/network/60-vif-emu.network \
"

do_install:append() {
    install -m 0644 ${WORKDIR}/60-vif-emu.network ${D}${sysconfdir}/systemd/network
}
