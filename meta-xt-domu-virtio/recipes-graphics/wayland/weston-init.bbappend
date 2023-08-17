FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://weston.sh \
"

FILES:${PN}:append = " \
    ${sysconfdir}/profile.d/weston.sh \
"

do_install:append() {
    # For virtio set XDG_RUNTIME_DIR to /run/user/$UID (e.g. run/user/0)
    install -d ${D}/${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/weston.sh ${D}/${sysconfdir}/profile.d/weston.sh
}
