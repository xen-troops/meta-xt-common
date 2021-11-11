IMAGE_INSTALL_append = " \
	      xen \
	      xen-tools-devd \
	      xen-tools-scripts-network \
	      xen-tools-scripts-block \
	      xen-tools-xenstore \
	      sndbe \
	      displbe \
	      camerabe \
	      xen-network \
	      dnsmasq \
	      optee-os \
	      ${@bb.utils.contains('DISTRO_FEATURES', 'ivi-shell', 'displaymanager', '', d)} \
	      "
