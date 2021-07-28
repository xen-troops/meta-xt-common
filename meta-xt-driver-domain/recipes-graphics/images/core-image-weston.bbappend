IMAGE_INSTALL_append = " \
	      xen-tools-devd \
	      xen-tools-scripts-network \
	      xen-tools-scripts-block \
	      xen-tools-xenstore \
	      sndbe \
	      displbe \
	      camerabe \
	      xen-network \
	      dnsmasq \
	      ${@bb.utils.contains('DISTRO_FEATURES', 'ivi-shell', 'displaymanager', '', d)} \
	      "
