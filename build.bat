echo Building cornos...
echo Please make sure you have java 8 installed!
echo .
echo ! WARNING !
echo Building it this way MAY FAIL! Do not complain in the issue section if it does
echo If it fails, try to use a prebuild or use intellij to build the mod.
echo ! WARNING !
echo .
echo To continue, press enter
pause > NUL
cls
gradlew.bat build
echo Done building.
echo Press enter to exit.
pause > NUL