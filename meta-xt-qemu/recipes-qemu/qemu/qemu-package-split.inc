PACKAGES:prepend:class-target = "${PN}-system-aarch64 "
FILES:${PN}-aarch64:class-target = "${bindir}/qemu-aarch64"

FILES:${PN}-system-aarch64:class-target = "${bindir}/qemu-system-aarch64"
RDEPENDS:${PN}-system-aarch64:append:class-target = "${PN}"
INSANE_SKIP:${PN}-system-aarch64:class-target = "file-rdeps"
