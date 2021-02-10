@echo off

rem Compile and build the PML project

call ppl compile_project --@input "file work/config/compiler.cfg"
if errorlevel 1 goto end

call ppl build_project --@input "file work/config/builder.cfg"

:end

pause
