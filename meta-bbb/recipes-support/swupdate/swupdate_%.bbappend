# Enable archive handler for SWUpdate

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://swupdate-archive.cfg"
