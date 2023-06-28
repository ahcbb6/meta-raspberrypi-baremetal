# meta-raspberrypi-baremetal

Layer compatible with **The Yocto Project/OpenEmbedded** to build baremetal
applications for the Raspberry Pi Pico boards.

This layer provides the functionality to OpenEmbedded of building your
own **newlib** toolchain for **ARM Cortex M0+** processors and use it to create
applications compatible with the **Raspberry Pi Pico** boards (*RP2040 microcontroller*).

Currently **MACHINE** should be set to *"raspberrypi-pico"* to build the samples.

The variable **RPI_PICO_SAMPLE** is used to choose the examples to build, by default,
the system will build all the examples, if desired, it may be set to the example
we wish to build, e.g. *"blink"*.

## Build Status

| master  | [![Build Status][masterBadge]][masterPipeline]   |
|:-------:|--------------------------------------------------|
| mickledore | [![Build Status][mickledoreBadge]][mickledorePipeline] |


[masterBadge]: https://dev.azure.com/ahcbb6/meta-raspberrypi-baremetal/_apis/build/status%2FRaspberry%20Pi%20Baremetal?branchName=master
[masterPipeline]: https://dev.azure.com/ahcbb6/meta-raspberrypi-baremetal/_build?definitionId=35&branchName=master
[mickledoreBadge]: https://dev.azure.com/ahcbb6/meta-raspberrypi-baremetal/_apis/build/status%2FRaspberry%20Pi%20Baremetal?branchName=mickledore
[mickledorePipeline]: https://dev.azure.com/ahcbb6/meta-raspberrypi-baremetal/_build?definitionId=35&branchName=mickledore


## Dependencies

This layer depends on:

     URI: git://git.yoctoproject.org/poky
     branch: master


## License
This layer has an MIT license (see LICENSE)


## Raspberry Pi baremetal build setup

1.- Clone the required repositories
```bash
$ git clone https://git.yoctoproject.org/git/poky
$ cd poky
$ git clone https://github.com/ahcbb6/meta-raspberrypi-baremetal.git
```
2.- Add meta-raspberrypi-baremetal to your bblayers.conf
```bash
$ source oe-init-build-env
$ bitbake-layers add-layer ../meta-raspberrypi-baremetal
```
3.- Add the required variables to your local.conf
```bash
$ echo "MACHINE = \"raspberrypi-pico\"" >> ./conf/local.conf
```

## Build the examples from the pico-examples repository:
4.- Build:
```bash
$ bitbake pico-examples
```
The compiled binaries are located inside the deploy directory, both the ELF and UF2 files
and can easily be flashed to the Raspberry Pi Pico via the usual methods.

```bash
$ ls tmp/deploy/images/raspberrypi-pico/
adc_console.elf
adc_console.uf2
blink.elf
blink.uf2
...
```

For testing purposes, these binaries can also be downloaded from the nightly pipeline build mentioned above.

