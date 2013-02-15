@echo off
echo Build Utility for MCError
echo Please specify the version number, followed by ENTER. (e.g. 25)
SET /P version=Version number: 

:: make directory so javac doesn't have to
erase /S /Q buildtemp
mkdir buildtemp\minecrafterror\resources

:: compile
echo Compiling
javac -d buildtemp\ src\minecrafterror\*.java src\minecrafterror\analysis\*.java
IF NOT ERRORLEVEL 0 GOTO compileerror

:: copy resources
XCOPY /S src\minecrafterror\resources buildtemp\minecrafterror\resources

:: remove any old jarfile, make a new one
:: the cd is because the jar utility is weird.
erase /Q MCErrorV%version%.jar
cd buildtemp
jar cvfe ..\MCErrorV%version%.jar minecrafterror.Main .
IF NOT ERRORLEVEL 0 GOTO packageerror
cd ..

echo Cleaning up
erase /S /Q buildtemp
echo Complete
exit /B 0

:compileerror
echo Compile error, please fix
pause
exit /B 1

:packageerror
echo Packaging error. Please check the contents of buildtemp/.
pause
exit /B 1