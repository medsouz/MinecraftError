#!/bin/bash
echo "Please specify the version number, followed by ENTER. (e.g. 25)"
read version

#make parents too
mkdir -p buildtemp/minecrafterror/resources
#compile
javac -d buildtemp/ src/minecrafterror/*.java
if [ $? -ne 0 ]
then
	exit 1 #on build fail
fi
#copy resources
cp src/minecrafterror/resources/* buildtemp/minecrafterror/resources/
# remove any old jarfile, make a new one
#the cd is because jar is weird.
rm MCErrorV${version}.jar
cd buildtemp
jar cvfe ../MCErrorV${version}.jar minecrafterror.Main .
cd ..
#make jarfile executable
chmod ugo+x MCErrorV${version}.jar
