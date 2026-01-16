SUMMARY= "Firmware version information"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://firmware-version"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/firmware-version ${D}${sysconfdir}/
}

FILES:${PN} = "${sysconfdir}/firmware-version"