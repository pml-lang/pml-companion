@echo off

rem Run the PML project's tests

setlocal
set THIS_DIR=%~dp0

cd %THIS_DIR%build
call java.exe --module-path lib;%PATH_TO_FX% --module dev.pml.converter/dev.pml.converter.se_tests_start %*

pause
