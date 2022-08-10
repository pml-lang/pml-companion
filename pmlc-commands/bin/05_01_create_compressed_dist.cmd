cd ..

rem call ..\gradlew distZip
call ..\gradlew assembleDist 
if errorlevel 1 goto FAILURE

exit /B 0

:FAILURE
pause
exit /B 1
