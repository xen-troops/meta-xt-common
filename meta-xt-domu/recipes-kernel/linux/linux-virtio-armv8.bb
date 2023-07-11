DESCRIPTION = "Virtio recipe for AArch64 kernels"

require recipes-kernel/linux/linux-yocto.inc

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids errors when trying
# to build multiple virtual/kernel providers, e.g. as dependency of
# core-image-rt-sdk, core-image-rt.
python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel", True) != "linux-virtio-armv8":
        raise bb.parse.SkipPackage("Set PREFERRED_PROVIDER_virtual/kernel to linux-virtio-armv8 to enable it")
}

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BSP_URL = "git://github.com/torvalds/linux.git"
BRANCH = "master"
SRCREV = "6995e2de6891c724bfeb2db33d7b87775f913ad1"

SRC_URI = "${BSP_URL};nocheckout=1;branch=${BRANCH};protocol=https"
SRC_URI:append = " file://defconfig"
SRC_URI:append = " file://0001-WIP-Use-INVALID_BACKEND_DOMID-for-the-untraslated-de.patch"
SRC_URI:append = " file://0002-DEBUG-Print-retrieved-stream-ID-backend_domid-for-bo.patch"

LINUX_VERSION = "6.4.0"
PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KMETA = "kernel-meta"

COMPATIBLE_MACHINE = "virtio-armv8-xt"
