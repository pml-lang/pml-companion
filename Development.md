# Development

## Overview

Development is supported on Linux, macOS, and Windows.

PML is written in PPL ([Practical Programming Language](https://ppl-lang.dev)). Before using PPL please have a look at the [FAQ](https://ppl-lang.dev/ppl/about/faq.html), and read [How mature is PPL?](https://ppl-lang.dev/ppl/about/faq.html#mature). 

You can optionally use the [Gradle Build Tool](https://gradle.org/) to compile PML, create distributions, and use all other features of Gradle.

## Installation

1. **Install PPL (required)**  
    
    To install PPL please follow the [online instructions](https://ppl-lang.dev/ppl/downloads). You also need to install JavaFX, which is an option for PPL.

3. **Download the PML source code**  
    
    The source code is available on [Github](https://github.com/pml-lang/converter).

    You can download the source code into any directory of your choice.

    To download the files into directory `pmlconverter` using Git, type:
    ```
    git clone https://github.com/pml-lang/converter pmlconverter
    cd pmlconverter
    ```

    If you use Linux, ensure that all `.sh` files in the root directory have execute permission.
    
## Compile and Build

To compile and build PML, execute the the `compile_and_build` OS script located in the root directory of your PML download (`compile_and_build.bat` on Windows, `compile_and_build.sh` on Linux). The output is stored in directory `build`.

Alternatively you can type `ppl compile` and `ppl build` in a terminal.

## Run

To run the PML application execute the `run` OS script (`run.bat` on Windows, `run.sh` on Linux).

Alternatively you can type `ppl run` in a terminal.

## Run Tests

To run the unit tests execute the `run_tests` OS script (`run_tests.bat` on Windows, `run_tests.sh` on Linux).

Alternatively you can type `ppl run_tests` in a terminal.

## Build a distribution

Type `ppl create_distribution` in a terminal. The output is stored in directory `distribution`.

## Using Gradle

Instead of using PPL's integrated build functionality, you can (alternatively) use the *Gradle Build Tool*.

If not done already, [install](https://gradle.org/install/) the latest version of Gradle.

To compile and build, type `gradle build`. The output is stored in directory `buildGradle`.

To run the PML application, type `gradle run`.

Running unit tests with Gradle is currently not supported.

To create a distribution, type `gradle distZip` (Windows) or `gradle distTar` (Linux).

To see the list of all available Gradle tasks, type `gradle tasks --all`.
