SUMMARY = "BBB Custom Image with OTA Update and WiFi Support"
DESCRIPTION = "Minimal image for BeagleBone Black with SWUpdate OTA client and USB WiFi driver"
LICENSE = "MIT"

inherit core-image

# Base system packages
IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

# ============================================================================
# SWUPDATE - OTA CLIENT
# ============================================================================
IMAGE_INSTALL:append = " \
    swupdate \
    swupdate-www \
    swupdate-progress \
    libubootenv-bin \
    swupdate-config \
"

# ============================================================================
# U-BOOT SUPPORT
# ============================================================================
IMAGE_INSTALL:append = " \
    u-boot-script \
"

# ============================================================================
# USB WIFI SUPPORT
# ============================================================================
IMAGE_INSTALL:append = " \
    rtl8188eu \
    linux-firmware \
"

# ============================================================================
# NETWORK UTILITIES
# ============================================================================
IMAGE_INSTALL:append = " \
    dhcpcd \
    iproute2 \
    iputils \
    wpa-supplicant \
    iw \
"

# ============================================================================
# SSH & REMOTE ACCESS
# ============================================================================
IMAGE_INSTALL:append = " \
    openssh \
    openssh-sftp-server \
"

IMAGE_FEATURES:append = " ssh-server-openssh"

# ============================================================================
# HTTP/HTTPS TOOLS (for OTA downloads)
# ============================================================================
IMAGE_INSTALL:append = " \
    curl \
    wget \
    ca-certificates \
"

# ============================================================================
# SYSTEM UTILITIES
# ============================================================================
IMAGE_INSTALL:append = " \
    util-linux \
    e2fsprogs \
    procps \
    coreutils \
    nano \
    kernel-modules \
    kernel-devicetree \
"

# ============================================================================
# C++ SUPPORT (if needed by your applications)
# ============================================================================
IMAGE_INSTALL:append = " \
    libgcc \
    libstdc++ \
    libatomic \
"

IMAGE_INSTALL:append = " version-info"

# ============================================================================
# IMAGE SETTINGS
# ============================================================================
IMAGE_ROOTFS_EXTRA_SPACE = "300000"

# Increase tmpfs to 768MB for SWUpdate archive extraction
IMAGE_INSTALL:append = " base-files"
FILESYSTEM_PERMS_TABLES = "${IMAGE_ROOTFS}/etc/fstab"

# Use custom WIC file for dual-boot
WKS_FILE = "bbb-dual-boot.wks"
# Use tar.gz for SWUpdate compatibility (gunzip handler)
IMAGE_FSTYPES = "ext4 tar.gz wic wic.gz wic.bmap"

# Bootloader dependencies - ensure all boot files are available for WIC
# Include kernel (zImage) and device tree for boot partition
IMAGE_BOOT_FILES = " \
    MLO \
    u-boot.img \
    boot.scr \
    uEnv.txt \
    zImage \
    am335x-boneblack.dtb \
"

# Enable root login for initial setup (disable in production!)
IMAGE_FEATURES:append = " allow-root-login"
IMAGE_FEATURES:append = " empty-root-password"