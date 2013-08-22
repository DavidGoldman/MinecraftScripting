package scripting.forge;

import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import scripting.ScriptingMod;
import scripting.core.ServerCore;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements IScheduledTickHandler {
	
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

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (lastThread != Thread.currentThread()) { 
			lastThread = Thread.currentThread();
			ScriptingMod.instance.clearSelections();
			core = new ServerCore(loadDir, props, abbreviations);
		}
		core.tick();
		
		List<EntityPlayerMP> players = (List<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		ScriptingMod.instance.updateSelections(players);
	}
	
	/**
	 * @return The ServerCore. Do not cache.
	 */
	public ServerCore getServerCore() {
		return core;
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "scripting.server";
	}

	@Override
	public int nextTickSpacing() {
		return 1;
	}

}
