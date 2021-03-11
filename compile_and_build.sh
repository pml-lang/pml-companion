#!/bin/sh

# Compile and build the PML code

ppl compile_project --@input "file work/config/compiler.cfg"
EXIT_CODE=$?

if [ "$EXIT_CODE" -eq 0 ] ; then
	ppl build_project --@input "file work/config/builder.cfg"
	EXIT_CODE=$?
fi

echo -n "Press <Enter> to continue: "
read tmp

exit $EXIT_CODE
