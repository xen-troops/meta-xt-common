require recipes-core/images/common_install.inc

IMAGE_INSTALL:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'ivi-shell', 'displaymanager', '', d)} \
"
