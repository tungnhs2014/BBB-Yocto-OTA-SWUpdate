DESCRIPTION = "SWUpdate configuration files for BeagleBone Black"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://swupdate.cfg \
    file://hwrevision \
    file://swupdate-suricatta.service \
"

# No compilation needed
do_compile[noexec] = "1"

do_install() {
    # Install swupdate.cfg to /etc/
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/swupdate.cfg ${D}${sysconfdir}/swupdate.cfg
    
    # Install hwrevision to /etc/
    install -m 0644 ${WORKDIR}/hwrevision ${D}${sysconfdir}/hwrevision

    # Install systemd service
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/swupdate-suricatta.service ${D}${systemd_unitdir}/system/
}

# Inherit systemd class
inherit systemd

# Enable service by default
SYSTEMD_SERVICE:${PN} = "swupdate-suricatta.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} = " \
    ${sysconfdir}/swupdate.cfg \
    ${sysconfdir}/hwrevision \
    ${systemd_unitdir}/system/swupdate-suricatta.service \
"

# Ensure this package is installed before swupdate service starts
RDEPENDS:${PN} = "swupdate"