FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-rcar-Use-UART-instead-of-Secure-DRAM-area-for-loggin.patch \
    file://0002-tools-Produce-two-cert_header_sa6-images.patch \
    file://0003-rcar-Add-BOARD_SALVATOR_X-case-in-ddr_rank_judge.patch \
"

ATFW_OPT_RPC = "${@oe.utils.conditional("DISABLE_RPC_ACCESS", "1", " RCAR_RPC_HYPERFLASH_LOCKED=1", "RCAR_RPC_HYPERFLASH_LOCKED=0", d)}"
ADDITIONAL_ATFW_OPT_append = " ${ATFW_OPT_RPC}"

SRC_URI_append = " \
    file://0004-Enable-DAVEHD-on-R-Car-M3.patch \
    file://0005-Change-the-security-attribute-setting-for-Cortex-R7-.patch \
    file://0006-Kick-CR7-in-bl2.patch \
    file://0007-plat-renesas-rcar-bl31-Enable-RPC-access-if-necessar.patch \
    file://0008-ATF-Add-additional-registers-logs.patch \
"

do_deploy_append () {
    install -m 0644 ${S}/tools/renesas/rcar_layout_create/bootparam_sa0.bin ${DEPLOYDIR}/bootparam_sa0.bin
    install -m 0644 ${S}/tools/renesas/rcar_layout_create/cert_header_sa6.bin ${DEPLOYDIR}/cert_header_sa6.bin
    install -m 0644 ${S}/tools/renesas/rcar_layout_create/cert_header_sa6_emmc.bin ${DEPLOYDIR}/cert_header_sa6_emmc.bin
    install -m 0644 ${S}/tools/renesas/rcar_layout_create/cert_header_sa6_emmc.srec ${DEPLOYDIR}/cert_header_sa6_emmc.srec
}

