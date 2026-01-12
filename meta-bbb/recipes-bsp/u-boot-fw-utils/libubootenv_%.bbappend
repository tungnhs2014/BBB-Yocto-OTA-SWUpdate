# Append recipe for libubootenv (U-Boot environment tools)
# Adds custom fw_env.config for BeagleBone Black

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Add custom fw_env.config
SRC_URI += "file://fw_env.config"

# Install to /etc/
do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

# Ensure file is packaged
FILES:${PN} += "${sysconfdir}/fw_env.config"
