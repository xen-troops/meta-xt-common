require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

UBOOT_CONFIG[doma] = "xenguest_arm64_android_defconfig"
UBOOT_CONFIG = "doma"

SRCREV = "${AUTOREV}"
UBOOT_SOURCE ??= "git://github.com/xen-troops/u-boot.git;protocol=https;branch=android-master;"
SRC_URI = "\
    ${UBOOT_SOURCE} \
"

FILES:${PN} = " \
    ${libdir}/xen/boot/u-boot-doma \
"

do_install() {
	install -d ${D}/${libdir}/xen/boot/
        # Trim spaces in ${UBOOT_MACHINE}
        UBOOT_BIN_PATH=`echo ${UBOOT_MACHINE} | xargs`
	install -m 0644 ${B}/${UBOOT_BIN_PATH}/u-boot.bin \
			${D}/${libdir}/xen/boot/u-boot-doma
}

do_deploy() {
	# Don't deploy anything
	return 0
}
