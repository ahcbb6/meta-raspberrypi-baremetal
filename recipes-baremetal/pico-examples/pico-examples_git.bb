SUMMARY = "Raspberry Pi Pico SDK Examples"
HOMEPAGE = "https://github.com/raspberrypi/pico-examples"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=db74c933ca4b8bb864b9088bec194057"


SRC_URI = "git://github.com/raspberrypi/pico-examples.git;protocol=https;branch=master;name=examples"

# We need the pico SDK to build
# TODO: use submodules fetcher for the SDK (it currently fails to recursively fetch submodules)
SRC_URI:append = " git://github.com/raspberrypi/pico-sdk.git;protocol=https;branch=master;name=sdk;destsuffix=pico-sdk"



# Patches required for the SDK to allow us to use our own toolchain
SRC_URI:append = " file://use-poky-triplet-compiler-in-SDK.patch;patchdir=../pico-sdk"
SRC_URI:append = " file://use-native-tools-in-SDK.patch;patchdir=../pico-sdk"

PICO_EXAMPLES_SRCREV ?= "eca13acf57916a0bd5961028314006983894fc84"
SRCREV_FORMAT ?= "examples_sdk"
SRCREV_examples = "${PICO_EXAMPLES_SRCREV}"
SRCREV_sdk = "${PICO_SDK_SRCREV}"
PV = "1.0+git${SRCPV}"


PICO_SDK_PATH = "${WORKDIR}/pico-sdk/"



B = "${WORKDIR}/build"
S = "${WORKDIR}/git"

DEPENDS = "elf2uf2-native pioasm-native"


# Override if we want to build only a subset of the samples
RPI_PICO_SAMPLE ?= "all"
OECMAKE_TARGET_COMPILE = " ${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'all', ' ${RPI_PICO_SAMPLE}', d)}"

# We need to set up a default to satisfy the baremetal-image class,
# even if we're building all examples, when building all examples
# blink gets to be linked, otherwise the chosen example does.
# potential breakage if we choose several we'd need to set this manually
BAREMETAL_BINNAME ?= "${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'blink-${MACHINE}', ' ${RPI_PICO_SAMPLE}-${MACHINE}', d)}"
IMAGE_LINK_NAME ?= "${@bb.utils.contains('RPI_PICO_SAMPLE', 'all', 'blink-image-${MACHINE}', ' ${RPI_PICO_SAMPLE}-image-${MACHINE}', d)}"


inherit cmake python3native baremetal-image

# This should only build with newlib, not even tclibc-baremetal
COMPATIBLE_HOST:libc-baremetal:class-target = "null"


# Match what pico-examples is using for easier development
OECMAKE_GENERATOR = "Unix Makefiles"

# Accommodate for the wiring in the Pico SDK
EXTRA_OECMAKE:append = " \
    -DPICO_BOARD=${RASPI_BOARD} \
    -DPICO_SDK_PATH=${PICO_SDK_PATH}  \
    -DPICO_GCC_TRIPLE=${TARGET_SYS} \
    -DPython3_EXECUTABLE=${PYTHON} \
    -DELF2UF2_FOUND=1 \
    -DELF2UF2_EXECUTABLE=${STAGING_BINDIR_NATIVE}/elf2uf2 \
    -DPioasm_FOUND=1 \
    -DPioasm_EXECUTABLE=${STAGING_BINDIR_NATIVE}/pioasm \
    -DCMAKE_VERBOSE_MAKEFILE:BOOL=ON \
    -UCMAKE_TOOLCHAIN_FILE \
"

# TODO: DEBUG_PREFIX_MAP really shouldnt be used, find a way to better
# propagate --sysroot= and arch + nopie, the cmake wiring in the SDK
# seems to like DEBUG_PREFIX_MAP and propagates it to all required variables
# (unlike CFLAGS, CXXFLAGS, LDFLAGS, etc which cause problems one way or another).
DEBUG_PREFIX_MAP:append = " ${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS}"
DEBUG_PREFIX_MAP:append = " --verbose -Wl,--verbose"


do_install(){
    install -d ${D}/${base_libdir}/firmware
    find ${B} -name "*.uf2" -o -name "*.elf" | while read binfile ; do
        install -m 0755 ${binfile} ${D}/${base_libdir}/firmware
    done

    # Remove references to build directories only from ELFs to avoid QA warning
    find ${D} -name "*.elf" | while read binfile ; do
        # sed corrupts the binaries
        ${OBJCOPY} --remove-section .debug_line ${binfile}
        ${OBJCOPY} --remove-section .debug_str ${binfile}
    done
}

do_image(){
    find ${D}/${base_libdir}/firmware -name "*.uf2" -o -name "*.elf" | while read binfile ; do
        install -m 0755 ${binfile} ${IMGDEPLOYDIR}/
    done
}

FILES:${PN} += " \
    ${base_libdir}/firmware/*.uf2 \
"

FILES:${PN}-dbg += " \
    ${base_libdir}/firmware/*.elf \
"
