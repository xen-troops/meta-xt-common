FILESEXTRAPATHS:append := "${THISDIR}/files:"

PR="r2"

SRC_URI:append = " \
    file://system.pa \
    file://daemon.conf \
    file://pulseaudio.service \
"

inherit systemd

FILES:${PN}-server += " \
    ${systemd_system_unitdir} \
"

SYSTEMD_PACKAGES = "${PN}-server"
SYSTEMD_SERVICE:${PN}-server = " pulseaudio.service"

set_cfg_value () {
	sed -i -e "s~\(; *\)\?$2 =.*~$2 = $3~" "$1"
	if ! grep -q "^$2 = $3\$" "$1"; then
		die "Use of sed to set '$2' to '$3' in '$1' failed"
	fi
}

do_install:append () {
    install -d ${D}/etc/pulse

    install -m 0644 ${WORKDIR}/system.pa ${D}/etc/pulse/system.pa
    install -m 0644 ${WORKDIR}/daemon.conf ${D}/etc/pulse/daemon.conf

    rm -rf ${D}/usr/lib/systemd
    rm ${D}/${sysconfdir}/pulse/default.pa

    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${systemd_system_unitdir}/sound.target.wants
    install -m 0644 ${WORKDIR}/pulseaudio.service ${D}${systemd_system_unitdir}
    ln -sf ${systemd_system_unitdir}/pulseaudio.service \
        ${D}${systemd_system_unitdir}/sound.target.wants/pulseaudio.service

    set_cfg_value ${D}/${sysconfdir}/pulse/client.conf autospawn no
    set_cfg_value ${D}/${sysconfdir}/pulse/client.conf default-server /var/run/pulse/native
}
