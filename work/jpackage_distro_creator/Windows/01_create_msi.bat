@echo off

cd ..
cd ..
cd ..

rem set NAME=PML_to_HTML_Converter
set NAME=PML to HTML Converter
set VERSION=1.2.0
set COPYRIGHT=Christian Neumanns
set DESCRIPTION=Practical Markup Language (PML) to HTML Converter
set OUPUT_DIR=jpackage_distro

set APP_IMAGE_PARENT_DIR=temp\app_image
set APP_IMAGE_DIR=%APP_IMAGE_PARENT_DIR%\%NAME%

rmdir "%APP_IMAGE_DIR%\" /s /q

rem Create application image
jpackage ^
    --name "%NAME%" ^
    --module-path build/lib;C:/aa/programs/javafx-jmods-15.0.1 ^
    --module dev.pml.converter/dev.pml.converter.se_start ^
    --type app-image ^
    --dest "%APP_IMAGE_PARENT_DIR%" ^
    --app-version "%VERSION%" ^
    --copyright "%COPYRIGHT%" ^
    --description "%DESCRIPTION%" ^
    --icon ../logo/pml.ico ^
    --add-launcher pmlc=work/jpackage_distro_creator/Windows/pmlc_Windows.properties

rem     --win-console ^
rem     --resource-dir work/resources ^
rem     --input ... is copied to app/
rem pause

rem Copy resource files
xcopy work\resources\*.* "%APP_IMAGE_DIR%\runtime\" /s
xcopy build\plib\*.* "%APP_IMAGE_DIR%\runtime\plib\" /s

rem Create distribution
jpackage ^
    --name "%NAME%" ^
    --app-image "%APP_IMAGE_DIR%" ^
    --type msi ^
    --dest "%OUPUT_DIR%/Windows" ^
    --app-version "%VERSION%" ^
    --copyright "%COPYRIGHT%" ^
    --license-file work/resources/LICENSE.txt ^
    --description "%DESCRIPTION%" ^
    --win-shortcut ^
    --win-menu ^
    --win-menu-group "Practical Markup Language (PML)"

rem    --win-dir-chooser

echo -
echo Will copy to local website dir.
pause
xcopy %OUPUT_DIR%\Windows\*.msi C:\aa\work\PML\website\public\downloads\windows

echo -
echo Will copy to public website (Q: must be open)
echo VERIFY DESTINATION DIRECTORY in file 01_create_msi.bat!!!
pause
xcopy %OUPUT_DIR%\Windows\*.msi Q:\public_html\pml-lang_org\downloads\windows

