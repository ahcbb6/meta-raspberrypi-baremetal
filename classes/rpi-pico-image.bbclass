inherit baremetal-image rpi-pico-sdk

do_install(){
    install -d ${D}/${base_libdir}/firmware
    find ${B} -name "*.uf2" -o -name "*.elf" | while read binfile ; do
        # boot stage 2 is built by the sdk but we probably dont need to install it on target
        if [ $(basename ${binfile}) != "bs2_default.elf" ]; then
            install -m 0755 ${binfile} ${D}/${base_libdir}/firmware
        fi
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
