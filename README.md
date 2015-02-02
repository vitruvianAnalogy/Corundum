Corundum
========

Description
-----------------
This project is a Java-based server-side plugin-based modification A.P.I. for Minecraft. Using Mojang's minecraft_server.jar as a resource, it can work like a Minecraft server while allowing user-programmed Java plugins to handle events that occur during the gameplay in order to modify the mechanics of Minecraft. This can be used to do small tasks like preventing the spawning of baby zombies or large, complex tasks such as creating minigames servers like MinePlex, The Shotbow Network, or HyPixel's server.

Installation
--------------
_NOTE: This product is not finished!_

Installing Normally
=========
_NOTE: There is no normal download yet!_

 1. If your Java version is less than 7, install your favorite Java 7+ Virtual Machine.

 2. Download the Corundum launcher .jar. _NOTE: There is not currently a Corundum launcher download!_

 3. Run the Corundum launcher .jar with your Java 7+ Virtual Machine.

The Corundum launcher will help you from there!

Building from Source
==========
You can either use the installer under the Corundum installer directory here, OR follow these steps.

 1. Download [MCP v9.08](http://www.mediafire.com/download/2czafa60rh4ajhj/mcp908.zip).
 2. Download [the minecraft_server.jar for Minecraft 1.8.1](https://s3.amazonaws.com/Minecraft.Download/versions/1.8.1/minecraft_server.1.8.1.jar) from [Minecraft's official website](https://minecraft.net/).
 3. Download the source code from this repository (at least both Corundum and the Corundum Hub).
 4. Put the minecraft_server.jar in MCP's "jar" folder and run MCP's decompile script (decompile.bat for Windows, decompile.sh for *nix-based systems, including Mac).
 5. Compile Corundum and the Corundum Hub with your favorite Java 7 compiler; if you had to combine the source files for Corundum and Hub to achieve this, separate them again into two separate folders.
 Pre-6. If this is your first time using MCP, you may have to run the recompile script before attempting the next steps.
 6. Copy the Corundum bytecode (.class files from the "org" folder) into MCP's "bin/minecraft_server" folder.
 7. Run MCP's reobfuscate script (in the MCP folder, ending in ".bat" for Windows machines or ending in ".sh" for Unix-based systems, including Mac).
 8. Package the "org" folder in MCP's "reobf" folder into a new jar called "Corundum.jar". Make sure that inside the jar, the top-level directory is "org".
 9. Repeat steps `5.` and `6.` for the Corundum Hub bytecode.
 10. Package the "org" folder in MCP's "reobf" folder into a new jar called "Corundum Hub.jar". Make sure that inside the jar, the top-level directory is "org".
 11. Add a META_INF folder with a MANIFEST.MF file to the Corundum Hub.jar and designate "org.corundummc.hub.CorundumHub" as the main class.
 12. Place the Corundum.jar, Corundum Hub.jar, and minecraft_server.jar in the same directory.
 13. Run the Corundum launcher.jar with a Java 7 Virtual Machine.

Sorry it's so complicated.
