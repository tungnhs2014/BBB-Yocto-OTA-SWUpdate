FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://uEnv.txt"

# FORCE REMOVE EXTLINUX to prevent interference with boot.scr
do_install:append() {
    rm -rf ${D}/boot/extlinux
    rm -f ${D}/boot/extlinux.conf
}

do_deploy:append() {
    install -m 0644 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}/uEnv.txt
    
    # Critical: Removing extlinux.conf to force U-Boot to use boot.scr
    rm -f ${DEPLOYDIR}/extlinux.conf*
    rm -rf ${DEPLOYDIR}/extlinux
}
