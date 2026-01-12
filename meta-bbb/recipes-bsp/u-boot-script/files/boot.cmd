# U-Boot Boot Script for BeagleBone Black Dual-Boot
# ==================================================

echo "=== BBB Dual-Boot Script ==="

# Device configuration
setenv devtype mmc
setenv devnum 0
setenv bootpart 1

# Boot failure limit
if test -z "${bootlimit}"; then
    setenv bootlimit 3
fi

# Initialize rootpart if not set (first boot)
if test -z "${rootpart}"; then
    echo "First boot - setting rootpart to 2 (partition A)"
    setenv rootpart 2
    setenv upgrade_available 0
    setenv bootcount 0
    saveenv
fi

echo "Current rootpart: ${rootpart}"
echo "Boot count: ${bootcount}"
echo "Upgrade available: ${upgrade_available}"

# Check for boot failures
if test "${upgrade_available}" = "1"; then
    echo "Update detected - checking boot count..."
    
    if test "${bootcount}" -gt "${bootlimit}"; then
        echo "! !! Boot failed ${bootcount} times !!!"
        echo "Rolling back to previous partition..."
        
        # Toggle partition
        if test "${rootpart}" = "2"; then
            setenv rootpart 3
            echo "Rolled back to partition 3 (B)"
        else
            setenv rootpart 2
            echo "Rolled back to partition 2 (A)"
        fi
        
        # Reset flags
        setenv upgrade_available 0
        setenv bootcount 0
        saveenv
    fi
fi

# Increment boot counter using plain arithmetic (setexpr may not be available)
if test "${bootcount}" = ""; then
    setenv bootcount 1
else
    # Simple increment without setexpr
    if test "${bootcount}" = "0"; then setenv bootcount 1; fi
    if test "${bootcount}" = "1"; then setenv bootcount 2; fi
    if test "${bootcount}" = "2"; then setenv bootcount 3; fi
    if test "${bootcount}" = "3"; then setenv bootcount 4; fi
fi
saveenv

# Set kernel boot arguments
setenv bootargs console=ttyS0,115200n8 root=/dev/mmcblk${devnum}p${rootpart} rootfstype=ext4 rootwait rw

# Load kernel and device tree from boot partition
echo "Loading kernel from partition ${bootpart}..."
load ${devtype} ${devnum}:${bootpart} ${loadaddr} /zImage
if test $? -ne 0; then
    echo "ERROR: Failed to load kernel!"
    exit
fi

echo "Loading device tree..."
load ${devtype} ${devnum}:${bootpart} ${fdtaddr} /am335x-boneblack.dtb
if test $? -ne 0; then
    echo "ERROR: Failed to load DTB!"
    exit
fi

# Boot kernel
echo "Booting Linux from partition ${rootpart}..."
bootz ${loadaddr} - ${fdtaddr}
