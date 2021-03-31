#!/usr/bin/env bash
echo "Building cornos..."
echo "Please make sure you have java 8 installed!"
echo ""
echo "! WARNING !"
echo "Building it this way MAY FAIL! Do not complain in the issue section if it does"
echo "If it fails, try to use a prebuild or use intellij to build the mod."
echo "! WARNING !"
echo ""
read -p "To continue, press enter"
clear
./gradlew build
echo "Done building."
read -p "Press Enter to exit. "