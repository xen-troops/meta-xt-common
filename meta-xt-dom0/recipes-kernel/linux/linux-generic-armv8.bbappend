FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BRANCH = "master"
SRCREV = "6995e2de6891c724bfeb2db33d7b87775f913ad1"
LINUX_VERSION = "6.4.0"

SRC_URI = " \
    git://github.com/torvalds/linux.git;branch=${BRANCH};protocol=https \
    file://defconfig \
  "
