FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH = "v5.10.41/rcar-5.1.4.1-xt0.1"
SRCREV = "d7c1b717e3f11b6aa0713dcefa8cf1ed4c1222af"
LINUX_VERSION = "5.10.41"

SRC_URI = " \
    git://github.com/xen-troops/linux.git;branch=${BRANCH};protocol=https \
    file://defconfig \
  "
