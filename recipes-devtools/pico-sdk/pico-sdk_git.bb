HOMEPAGE = "https://github.com/raspberrypi/pico-sdk"
SUMMARY= "The Raspberry Pi Pico SDK (henceforth the SDK) provides the headers, libraries and build system necessary to write programs for the RP2040-based devices such as the Raspberry Pi Pico in C, C++ or assembly language."

LICENSE="BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=db74c933ca4b8bb864b9088bec194057"

# TODO: use submodules fetcher for the SDK (it currently fails to recursively fetch submodules)
SRC_URI = "git://github.com/raspberrypi/pico-sdk.git;protocol=https;branch=master;destsuffix=pico-sdk-git"

# Patches required to allow us to use our own toolchain
SRC_URI:append = " file://use-poky-triplet-compiler-in-SDK.patch"
SRC_URI:append = " file://use-native-tools-in-SDK.patch"
SRCREV = "${PICO_SDK_SRCREV}"

PICO_SDK_INSTALLDIR = "${localstatedir}/pico-sdk/"

do_configure(){
:
}

do_compile(){
:
}

do_install(){
    install -d ${D}${PICO_SDK_INSTALLDIR}
    cp -r ${S}/* ${D}${PICO_SDK_INSTALLDIR}
}

SYSROOT_DIRS += "${PICO_SDK_INSTALLDIR}"
INHIBIT_SYSROOT_STRIP = "1"
BBCLASSEXTEND = "native"
