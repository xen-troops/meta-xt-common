# Qemu is necessary on ARM platforms, and to support HVM guests on x86
QEMU_HVM_DEFAULT = "qemu ${@bb.utils.contains('DISTRO_FEATURES', 'vmsep', 'qemu-system-i386', '', d)}"
QEMU = "${@bb.utils.contains('PACKAGECONFIG', 'hvm', '${QEMU_HVM_DEFAULT}', '', d)}"
QEMU:arm = "qemu ${@bb.utils.contains('DISTRO_FEATURES', 'vmsep', 'qemu-system-i386', '', d)}"
QEMU:aarch64 = "qemu ${@bb.utils.contains('DISTRO_FEATURES', 'vmsep', 'qemu-system-aarch64', '', d)}"

QEMU_ARCH = "i386"
QEMU_ARCH:aarch64 = "aarch64"

EXTRA_OECONF:remove = " --with-system-qemu=${bindir}/qemu-system-i386"

EXTRA_OECONF:append = " --with-system-qemu=${bindir}/qemu-system-${QEMU_ARCH}"
