package scripting.forge;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import scripting.ScriptingMod;
import scripting.core.ServerCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class ServerTickHandler {

	private final File loadDir;
	private final Map<String, Object> props;
	private final Map<String, Class<?>> abbreviations;

	private ServerCore core;
	private Thread lastThread;

	public ServerTickHandler(File scriptDir, Map<String, Object> props, Map<String, Class<?>> abbreviations) {
		this.loadDir = new File(scriptDir, "server");
		this.props = props;
		this.abbreviations = abbreviations;
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void tickEnd(ServerTickEvent event) {
		if (event.phase == Phase.START) {
			if (lastThread != Thread.currentThread()) { 
				lastThread = Thread.currentThread();
				ScriptingMod.instance.clearSelections();
				core = new ServerCore(loadDir, props, abbreviations);
			}
			core.tick();

			List<EntityPlayerMP> players = (List<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			ScriptingMod.instance.updateSelections(players);
		}
	}

	/**
	 * @return The ServerCore. Do not cache.
	 */
	public ServerCore getServerCore() {
		return core;
	}

}
