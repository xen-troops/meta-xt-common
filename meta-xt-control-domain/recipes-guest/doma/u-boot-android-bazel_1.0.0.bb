SUMMARY = "Deployment of Android U-Boot, which is built by the Bazel build system."
DESCRIPTION = "Android U-Boot is a standard tool to boot Android."

inherit base

# PARAMETERS

UBOOT_REPO_GIT_URL ?= "github.com/xen-troops/android_u-boot_manifest"
UBOOT_REPO_DOWNLOAD_PROTOCOL ?= "https"
UBOOT_REPO_GIT_BRANCH ?= "u-boot-mainline-xt"
UBOOT_REPO_MANIFEST ?= "default.xml"
U_BOOT_BUILD_TARGET ?= "xen_aarch64"

SRC_URI:append = "file://0001-xenvm-do-not-use-persistent-storage-for-bootconfig.patch;patchdir=${WORKDIR}/repo/u-boot"

DEPENDS += "rsync-native"

# IMPLEMENTATION

SRC_URI = "\
           repo://${UBOOT_REPO_GIT_URL};protocol=${UBOOT_REPO_DOWNLOAD_PROTOCOL};branch=${UBOOT_REPO_GIT_BRANCH};manifest=${UBOOT_REPO_MANIFEST}  \
           "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${WORKDIR}/repo/u-boot/Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

FILES:${PN} = "\
               ${libdir}/xen/boot/u-boot-doma \
               "

do_compile() {
    cd ${WORKDIR}/repo;
    export CC=""
    export CXX=""
    export LD=""
    export LDFLAGS=""
    export CFLAGS=""
    export CXXFLAGS=""
    # Use it instead if you want to get more debug build artifacts.
    # tools/bazel run //u-boot:${U_BOOT_BUILD_TARGET}_dist --verbose_failures --sandbox_debug -- --dist_dir=${B};
    tools/bazel --max_idle_secs=1 run //u-boot:${U_BOOT_BUILD_TARGET}_dist -- --dist_dir=${B};
    BUILD_RESULT=$(echo $?);
    tools/bazel clean --expunge;
    tools/bazel shutdown;
    exit ${BUILD_RESULT};
}

do_install() {
    install -d ${D}/${libdir}/xen/boot/
    install -m 0644 ${B}/u-boot.bin \
        ${D}/${libdir}/xen/boot/u-boot-doma
}
