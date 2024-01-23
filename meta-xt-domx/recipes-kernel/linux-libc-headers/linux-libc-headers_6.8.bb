require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = "git://github.com/torvalds/linux.git"
BRANCH = "master"
SRCREV = "6613476e225e090cc9aad49be7fa504e290dd33d"
LINUX_VERSION = "6.8.0-rc1"

SRC_URI = "${KERNEL_URL};branch=${BRANCH};protocol=https"


LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
