# Instructions
1. Click on "Downloads" above and to the right, under "Graphs".
2. Download the latest .jar or .exe file
3. Run MCErrorVxx.jar by double-clicking on it.
 * If you need to, right-click -> open with -> Java Runtime.
4. Click "Launch Minecraft" to start Minecraft.
5. At this point, if you are on a Mac, please click Issues above and paste what you get.
6. Now, login and (if applicable) play Minecraft until the error occurs.
 * If you get a black screen, wait 5 seconds, then close Minecraft.
7. **Read the automatic analysis.**
8. _If you still need to_, click "Paste Error" and copy the link.
9. Give the link to the problem-solvers.
10. If the problem-solvers ask for ModLoader.txt, click "Paste ModLoader.txt".


# About
This tool is used to find errors with Minecraft mods.
To use simply run the program and press "Launch Minecraft" and it should log all of your errors.

## Credits
* medsouz made the original code
* Malqua made the new GUI
* Riking made the error analyzer

#### build command
cd src<br>
javac minecrafterror/*.java<br>
jar cvfe ../MCErrorV25.jar minecrafterror.Main .