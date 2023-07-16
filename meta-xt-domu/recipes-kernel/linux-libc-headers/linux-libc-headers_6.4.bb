require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = "git://github.com/torvalds/linux.git"
BRANCH = "master"
SRCREV = "6995e2de6891c724bfeb2db33d7b87775f913ad1"
LINUX_VERSION = "6.4.0"

SRC_URI = "${KERNEL_URL};branch=${BRANCH};protocol=https"


LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
