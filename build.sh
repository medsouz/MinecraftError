#!/bin/bash
echo "Please specify the version number, followed by ENTER. (e.g. 25)"
read version
# make directory so javac doesn't have to
mkdir -p buildtemp/minecrafterror/resources
echo "Compiling"
# compile
javac -d buildtemp/ src/minecrafterror/*.java src/minecrafterror/analysis/*.java
if [ $? -ne 0 ]
then
	exit 1 # on build fail
fi
# copy resources
cp src/minecrafterror/resources/* buildtemp/minecrafterror/resources/
# remove any old jarfile, make a new one
# the cd is because the jar utility is weird.
rm MCErrorV${version}.jar
cd buildtemp
jar cvfe ../MCErrorV${version}.jar minecrafterror.Main .
cd ..
#make jarfile executable
chmod ugo+x MCErrorV${version}.jar
echo "Cleaning up"
rm -r buildtemp/
echo "Complete"
