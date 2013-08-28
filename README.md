In-Game MCEdit Filters & Scripts
=================

Forum Link: http://www.minecraftforum.net/topic/1951378-/

If you're trying to clone this repo, be sure to install the following dependencies:
* [Mozilla Rhino](https://github.com/mozilla/rhino)
* [GuiLib](https://github.com/DavidGoldman/GuiLib)

This mod adds Javascript support to Minecraft via Mozilla Rhino.

* Users can run regular scripts (extension .js) in-game on either the client or server.
 * To start/stop these scripts, press the binded key (default F7) to open the mod's GUI.
 * Support for these regular scripts is currently in the works - they are lacking a lot of features at the moment. 

* Users can also run filter scripts (extension .filter) which can directly modify the minecraft world. Think of them as a map-making tool - they can modify blocks, entities, and tile entities. Some filters are included with the mod by default. 

* Scripts are currently saved in the "/scripts/client" and "/scripts/server" folders which can be found in the user's minecraft directory. 

* To see what functions are available to the scripts, check out all of the classes inside the "scripting.wrapper" package. Note that some of the class names may be abbreviated to the scripts - check "scripting.ScriptingMod.java" to see which classes have shortened names. 


