SUMMARY = "Pioasm tool provided by the Pico SDK"

require pico-sdk-tools.inc

EXTRA_OECMAKE:append = " \
    -DPIOASM_VERSION_STRING=${PICO_SDK_VERSION} \
"
