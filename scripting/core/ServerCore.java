package scripting.core;

import static scripting.ScriptingMod.SECTION;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import scripting.core.script.BasicScript;
import scripting.core.script.FilterScript;
import scripting.core.script.JSScript;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ServerCore extends ScriptCore {

	public ServerCore(File dir, Map<String, Object> props, Map<String, Class<?>> abbreviations) {
		super(ScriptLoader.loadAllScripts(dir, false), props, abbreviations);
		
		System.out.println("[SCRIPTS] Server core initialized on " + Thread.currentThread());
	}
	
	@Override
	public boolean hasScripts() {
		if (scripts.isEmpty())
			return false;
		for (JSScript script : scripts.values())
			if (script instanceof BasicScript)
				return true;
		return false;
	}
	
	public List<String> getFilterScripts() {
		List<String> filters = new ArrayList<String>();
		for (JSScript script : scripts.values())
			if (script instanceof FilterScript) //Remove .filter extension
				filters.add(script.name.substring(0,script.name.length() - 7));
		Collections.sort(filters);
		return filters;
	}

	/**
	 * Notifies admins of the script crash.
	 */
	@Override
	protected void notifyCrash(BasicScript script, Exception e) {
		PacketDispatcher.sendPacketToAllPlayers(ScriptPacket.getPacket(PacketType.CLOSE_GUI));
		
		ServerConfigurationManager manager = MinecraftServer.getServer().getConfigurationManager();
		List<EntityPlayer> players = (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for (EntityPlayer p : players) {
			if(manager.areCommandsAllowed(p.getCommandSenderName())) {
				p.addChatMessage(SECTION + "cServer script \"" + script.name + "\" has crashed");
				p.addChatMessage(SECTION + "cCheck the console/log for more information");
				p.addChatMessage(SECTION + "cFor now, the script has been disabled");
				p.addChatMessage(SECTION + "cFix the script and restart the server to reload it");
			}
		}
	}

	public void runFilter(EntityPlayer player, String name) {
		if (!name.endsWith(".filter"))
			name += ".filter";
		JSScript script = scripts.get(name);
		if (script instanceof FilterScript) {
			
		}
		else
			player.addChatMessage(SECTION + "cUnknown filter \"" + name + "\"");
	}


}