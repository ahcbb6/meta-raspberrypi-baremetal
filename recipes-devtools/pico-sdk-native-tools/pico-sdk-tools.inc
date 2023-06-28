HOMEPAGE="https://github.com/raspberrypi/pico-sdk"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.TXT;md5=db74c933ca4b8bb864b9088bec194057"

SRC_URI = "git://github.com/raspberrypi/pico-sdk.git;protocol=https;branch=master"
SRCREV="6a7db34ff63345a7badec79ebea3aaef1712f374"
PV = "1.0+git${SRCPV}"

B="${WORKDIR}/build"
S="${WORKDIR}/git/tools/${BPN}"

inherit cmake native

do_install(){
    install -d ${D}${bindir}
    install -m 0775 ${B}/${BPN} ${D}/${bindir}/
}

OECMAKE_GENERATOR = "Unix Makefiles"
