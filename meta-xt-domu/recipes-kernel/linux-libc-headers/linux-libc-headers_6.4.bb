require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

BSP_URL = "git://github.com/torvalds/linux.git"
BRANCH = "master"
SRCREV = "6995e2de6891c724bfeb2db33d7b87775f913ad1"

SRC_URI = "${BSP_URL};branch=${BRANCH};protocol=https"

LINUX_VERSION = "6.4.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
