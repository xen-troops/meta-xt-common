# meta-xt-common #

This repository contains base Yocto layers for Xen Troops distro.
Layers in this repository are hardware- and product-independent. They
provide common facilities that may be used by any xt-based product.

Those layers *may* be added and used manually, but they were written
with [Moulin](https://moulin.readthedocs.io/en/latest/) build system,
as Moulin-based project files provide correct entries in local.conf

List of layers:

* meta-xt-dom0 - recipes for *"thin"* Dom0, which does not have
  access to HW and is booted from ramdisk. Main idea behind such thin
  Dom0 is to move all HW-dependend code into separate domain, that
  theoretically can be rebooted in runtime, without rebooting the
  whole platform.

* meta-xt-domu - recipes for generic user domain. This is
  unprivileged domain that generally have no access to real hardware.

* meta-xt-domx - shared recipes that can be used by any domain
  type.

* meta-xt-driver-domain - recipes for driver domain. This domain have
  access to real hardware and provides PV backends for other domain
  types, so they, say, can access network on display something on
  screen. Ideally, this layer can be applied either on top of Dom0, or
  on top of separate DomD.

* meta-xt-control-domain - recipes for control domain. This is the
  basically dom0 in Xen terms. This layer hold recipes that are
  responsible or creating other domain types during boot.
