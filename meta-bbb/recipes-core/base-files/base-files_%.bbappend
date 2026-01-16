FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install:append() {
    # Increase tmpfs to 768MB for SWUpdate operations
    if grep -q "^tmpfs.*/tmp" ${D}${sysconfdir}/fstab; then
        sed -i 's|tmpfs.*/tmp.*tmpfs.*defaults.*0.*0|tmpfs /tmp tmpfs defaults,size=768M 0 0|' ${D}${sysconfdir}/fstab
    else
        echo "tmpfs /tmp tmpfs defaults,size=768M 0 0" >> ${D}${sysconfdir}/fstab
    fi
}
