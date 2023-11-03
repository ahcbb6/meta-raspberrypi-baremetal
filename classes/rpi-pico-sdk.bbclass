
PICO_SDK_PATH ?= "${STAGING_DIR_NATIVE}${localstatedir}/pico-sdk/"

DEPENDS:append = " pico-sdk-native elf2uf2-native pioasm-native"

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

