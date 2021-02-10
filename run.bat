@echo off

rem Run the PML application

setlocal
set THIS_DIR=%~dp0

cd %THIS_DIR%build
call java.exe --module-path lib;%PATH_TO_FX% --module dev.pml.converter/dev.pml.converter.se_start %*

pause
