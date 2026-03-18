
PICO_SDK_PATH:class-target ?= "${STAGING_DIR_NATIVE}${localstatedir}/pico-sdk/"
PICO_SDK_PATH:class-native ?= "${localstatedir}/pico-sdk/"

DEPENDS:append = " pico-sdk-native pioasm-native"
DEPENDS:class-target:append = " picotool-native"

# Accommodate for the wiring in the Pico SDK
EXTRA_OECMAKE:append = " \
    -DPICO_BOARD=${RASPI_BOARD} \
    -DPICO_SDK_PATH=${PICO_SDK_PATH}  \
    -DPICO_GCC_TRIPLE=${TARGET_SYS} \
    -Dpioasm_FOUND=1 \
    -Dpioasm_EXECUTABLE=${STAGING_BINDIR_NATIVE}/pioasm \
    -Dpicotool_FOUND=1 \
    -DPICOTOOL_EXECUTABLE=${STAGING_BINDIR_NATIVE}/picotool \
    -DCMAKE_VERBOSE_MAKEFILE:BOOL=ON \
    -UCMAKE_TOOLCHAIN_FILE \
    -DCMAKE_EXE_LINKER_FLAGS='${LDFLAGS}' \
    -DCMAKE_COMMON_FLAGS_APPEND='${TUNE_CCARGS}${GLIBC_64BIT_TIME_FLAGS} ${SECURITY_NOPIE_CFLAGS} ${TOOLCHAIN_OPTIONS} ${DEBUG_PREFIX_MAP}' \
"
