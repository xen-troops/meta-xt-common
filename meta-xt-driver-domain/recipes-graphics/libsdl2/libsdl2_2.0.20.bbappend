
PACKAGECONFIG:append = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', ' kmsdrm gles2 ', d)}"
