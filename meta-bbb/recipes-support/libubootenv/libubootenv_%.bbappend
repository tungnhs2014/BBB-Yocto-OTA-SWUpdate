FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Add custom fw_env.config for BeagleBone Black
SRC_URI += "file://fw_env.config"

do_install:append() {
    # Install custom fw_env.config for BBB with FAT boot partition
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
