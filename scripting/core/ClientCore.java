package scripting.core;

import java.io.File;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import scripting.core.script.BasicScript;
import scripting.core.script.JSScript;
import scripting.gui.ClientMenu;
import static scripting.ScriptingMod.SECTION;

public class ClientCore extends ScriptCore {

	public ClientCore(File dir, Map<String, Object> props, Map<String, Class<?>> abbreviations) {
		super(ScriptLoader.loadAllScripts(dir, true), props, abbreviations);

		System.out.println("[SCRIPTS] Client core initialized on " + Thread.currentThread());
	}

	/**
	 * Notifies the player of the script crash.
	 */
	@Override
	protected void notifyCrash(BasicScript script, Exception e) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (player != null) {
			if (mc.currentScreen instanceof ClientMenu)
				mc.displayGuiScreen(null);
			player.addChatMessage(SECTION + "cClient script \"" + script.name + "\" has crashed");
			player.addChatMessage(SECTION + "cCheck the console/log for more information");
			player.addChatMessage(SECTION + "cFor now, the script has been disabled");
			player.addChatMessage(SECTION + "cFix the script and restart Minecraft to reload it");
		}
	}

}
