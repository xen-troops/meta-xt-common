FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "file://dbus.service \
                  file://dbus.socket \
                  file://dbus_env.conf \
                 "

inherit systemd

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -p -D ${WORKDIR}/dbus.service ${D}${systemd_user_unitdir}/dbus.service
        install -p -D ${WORKDIR}/dbus.socket ${D}${systemd_user_unitdir}/dbus.socket
        install -p -D ${WORKDIR}/dbus_env.conf ${D}${systemd_system_unitdir}/user@.service.d/dbus_env.conf

        # Execute these manually on behalf of systemctl script (from systemd-systemctl-native.bb)
        # because it does not support systemd's user mode.
        mkdir -p ${D}/etc/systemd/user/default.target.wants/
        ln -sf ${systemd_user_unitdir}/dbus.socket ${D}/etc/systemd/user/default.target.wants/dbus.socket
    fi
}

FILES_${PN} += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${systemd_user_unitdir}/dbus.*', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${systemd_system_unitdir}/user@.service.d/dbus_env.conf', '', d)} \
    "
