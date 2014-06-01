package scripting.forge;

import java.io.File;
import java.util.Map;

import net.minecraft.client.Minecraft;
import scripting.ScriptingMod;
import scripting.core.ClientCore;
import scripting.core.ScriptCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class ClientTickHandler {

	private static final int SELECTION_UPDATE = 5;

	private int counter;
	private ClientCore clientCore;

	public ClientTickHandler(File scriptDir, Map<String, Object> props, Map<String, Class<?>> abbreviations) {
		this.clientCore = new ClientCore(new File(scriptDir, "client"), props, abbreviations);
	}

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		if (event.phase == Phase.START && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
			clientCore.tick();

			if (++counter == SELECTION_UPDATE) {
				ScriptingMod.proxy.update();
				counter = 0;
			}
		}
	}

	public ScriptCore getClientCore() {
		return clientCore;
	}

}
