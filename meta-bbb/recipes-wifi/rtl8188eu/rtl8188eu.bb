DESCRIPTION = "Realtek RTL8188EU USB WiFi driver"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://github.com/lwfinger/rtl8188eu.git;branch=v5.2.2.4;protocol=https"
SRCREV = "${AUTOREV}"

PV = "5.2.2.4+git${SRCPV}"

S = "${WORKDIR}/git"

inherit module

KERNEL_MODULE_AUTOLOAD += "8188eu"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/
    install -m 0644 ${S}/8188eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8188eu.ko
}

# Force the .ko file into the main package to avoid splitting issues
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8188eu.ko"
RPROVIDES:${PN} += "kernel-module-8188eu"
