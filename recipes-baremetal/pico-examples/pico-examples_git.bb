SUMMARY = "Raspberry Pi Pico SDK Examples"
HOMEPAGE = "https://github.com/raspberrypi/pico-examples"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=db74c933ca4b8bb864b9088bec194057"

SRC_URI = "git://github.com/raspberrypi/pico-examples.git;protocol=https;branch=master"

SRC_URI:append = " file://use-native-tools.patch"

PICO_EXAMPLES_SRCREV ?= "eca13acf57916a0bd5961028314006983894fc84"
SRCREV = "${PICO_EXAMPLES_SRCREV}"
PV = "1.0+git${SRCPV}"

B = "${UNPACKDIR}/build"

# Override if we want to build only a subset of the samples
RPI_PICO_SAMPLE ?= "all"
OECMAKE_TARGET_COMPILE = " ${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'all', ' ${RPI_PICO_SAMPLE}', d)}"

# We need to set up a default to satisfy the baremetal-image class,
# even if we're building all examples, when building all examples
# blink gets to be linked, otherwise the chosen example does.
# potential breakage if we choose several we'd need to set this manually
BAREMETAL_BINNAME ?= "${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'blink-${MACHINE}', ' ${RPI_PICO_SAMPLE}-${MACHINE}', d)}"
IMAGE_LINK_NAME ?= "${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'blink-image-${MACHINE}', ' ${RPI_PICO_SAMPLE}-image-${MACHINE}', d)}"

# rpi-pico-image makes the pico-sdk available in PICO_SDK_PATH and installs/deploys .elf and .uf2 binaries automatically

inherit cmake python3native rpi-pico-image

# This should only build with newlib, not even tclibc-baremetal
COMPATIBLE_HOST:libc-baremetal:class-target = "null"


# Match what pico-examples is using for easier development
OECMAKE_GENERATOR = "Unix Makefiles"

