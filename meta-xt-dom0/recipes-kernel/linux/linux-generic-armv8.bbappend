FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BRANCH = "v5.10.41/rcar-5.1.4.1-xt0.2"
SRCREV = "${AUTOREV}"
LINUX_VERSION = "5.10.41"

SRC_URI = " \
    git://github.com/xen-troops/linux.git;branch=${BRANCH};protocol=https \
    file://defconfig \
  "
