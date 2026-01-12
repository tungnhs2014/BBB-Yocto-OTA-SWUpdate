DESCRIPTION = "U-Boot boot script for dual-boot system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "u-boot-mkimage-native"

SRC_URI = "file://boot.cmd"

S = "${WORKDIR}"

inherit deploy

do_compile() {
    # Compile boot.cmd to boot.scr using mkimage
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
        -n "BBB Dual-Boot Script" \
        -d ${WORKDIR}/boot.cmd ${B}/boot.scr
}

do_install() {
    install -d ${D}/boot
    install -m 0644 ${B}/boot.scr ${D}/boot/boot.scr
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/boot.scr ${DEPLOYDIR}/boot.scr
}

addtask deploy before do_build after do_compile

FILES:${PN} = "/boot/boot.scr"

# This package provides boot script
RPROVIDES:${PN} = "u-boot-script"
