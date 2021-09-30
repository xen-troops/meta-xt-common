require inc/xen-install.inc

IMAGE_INSTALL_append = " \
              sndbe \
              displbe \
              camerabe \
              ${@bb.utils.contains('DISTRO_FEATURES', 'ivi-shell', 'displaymanager', '', d)} \
              "
