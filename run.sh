#!/bin/sh

# Run the PML application

THIS_DIR=$(dirname "$0")

cd "$THIS_DIR/build"
java --module-path "lib:$PATH_TO_FX" --module dev.pml.converter/dev.pml.converter.se_start $*
EXIT_CODE=$?

echo -n "Press <Enter> to continue: "
read tmp

exit $EXIT_CODE
