HOMEPAGE = "https://github.com/raspberrypi/picotool"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.TXT;md5=db74c933ca4b8bb864b9088bec194057"

SRC_URI = "git://github.com/raspberrypi/picotool.git;protocol=https;branch=master"

# It seems picotool wont necessarily match the version of the pico-sdk
require pico-sdk-version.inc
SRCREV = "${PICOTOOL_SRCREV}"
PV = "${PICO_SDK_VERSION}+git${SRCPV}"

S = "${UNPACKDIR}/${BP}"

inherit cmake native rpi-pico-sdk

do_install(){
    install -d ${D}${bindir}
    install -m 0775 ${B}/${BPN} ${D}/${bindir}/
}

OECMAKE_GENERATOR = "Unix Makefiles"
