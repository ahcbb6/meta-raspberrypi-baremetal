# We want to use the FreeRTOS version provided by the meta-freertos layer
FREERTOS_BUNDLE = "0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a670e5d5245e0ffae9782ea5e22af84e"

inherit freertos-image cmake rpi-pico-image

S="${WORKDIR}/app"


SRC_URI += " \
    git://github.com/ahcbb6/FreeRTOS-raspberrypi-pico.git;name=app;destsuffix=app;branch=main;protocol=https \
"
SRCREV_FORMAT = "freertos_app"
SRCREV_app = "9518ff491c62be40c03b0dc6f01863c58c320130"


EXTRA_OECMAKE:append = " -DFREERTOS_KERNEL_SRC=${FREERTOS_KERNEL_SRC}"
