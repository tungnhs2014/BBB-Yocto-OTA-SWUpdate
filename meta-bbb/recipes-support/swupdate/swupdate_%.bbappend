# Enable archive handler and U-Boot support for SWUpdate

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://fragment.cfg"

# Ensure libubootenv is available for U-Boot variable manipulation
DEPENDS += "libubootenv"
RDEPENDS:${PN} += "libubootenv-bin"
