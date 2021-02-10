@echo off

call "C:\Program Files (x86)\NSIS\makensis.exe" PML_distro.nsi

cd ..
cd ..
cd ..
cd ..
cd

echo -
echo Will copy to local website dir.
pause
xcopy jpackage_distro\Windows\*.exe C:\aa\work\PML\website\public\downloads\windows

echo -
echo Will copy to public website (Q: must be open)
pause
xcopy jpackage_distro\Windows\*.exe Q:\public_html\practical-programming_org\pml\downloads\windows

pause
