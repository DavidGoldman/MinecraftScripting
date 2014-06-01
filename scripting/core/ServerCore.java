package scripting.core;

import static scripting.ScriptingMod.SECTION;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import scripting.Config;
import scripting.ScriptingMod;
import scripting.Selection;
import scripting.core.script.BasicScript;
import scripting.core.script.FilterScript;
import scripting.core.script.JSScript;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SettingsPacket;
import scripting.wrapper.ScriptSelection;
import scripting.wrapper.entity.ScriptPlayer;
import scripting.wrapper.settings.Setting;
import scripting.wrapper.world.ScriptWorld;

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
	 * Notifies OPs/whitelisted users of the script crash.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void notifyCrash(JSScript script, Exception e) {
		ScriptingMod.DISPATCHER.sendToAll(ScriptPacket.getPacket(PacketType.CLOSE_GUI));

		List<EntityPlayer> players = (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for (EntityPlayer p : players) 
			if(Config.hasPermission(p)) {
				sendMessageToPlayer(SECTION + "cServer script \"" + script.name + "\" has crashed", p);
				sendMessageToPlayer(SECTION + "cCheck the console/log for more information", p);
				sendMessageToPlayer(SECTION + "cFor now, the script has been disabled", p);
				sendMessageToPlayer(SECTION + "cFix the script and restart the server to reload it", p);
			}

	}

	public void runFilter(EntityPlayerMP player, String name) {
		if (!name.endsWith(".filter"))
			name += ".filter";
		JSScript script = scripts.get(name);
		if (script instanceof FilterScript) {
			FilterScript fs = (FilterScript) script;
			Selection sel = ScriptingMod.instance.getSelection(player);
			if (sel.isEmpty()) 
				sendMessageToPlayer(SECTION + "cNo selection! Unable to run filter", player);
			else if (fs.hasOptions()) 
				sendFilterOptions(player, fs);
			else 
				runFilter(player, sel, null, fs);
		}
		else
			sendMessageToPlayer(SECTION + "cUnknown filter \"" + name + "\"", player);
	}

	public void runFilter(EntityPlayerMP player, SettingsPacket pkt) {
		JSScript script = scripts.get(pkt.script);
		if (script instanceof FilterScript) {
			Selection sel = ScriptingMod.instance.getSelection(player);
			if (sel.isEmpty()) 
				sendMessageToPlayer(SECTION + "cNo selection! Unable to run filter", player);
			else {
				Map<String, Object> options = new HashMap<String, Object>();
				for (Setting s : pkt.settings)
					options.put(s.display, s.getValue());
				runFilter(player, sel, options, (FilterScript)script);
			}
		}
		else
			sendMessageToPlayer(SECTION + "cError finding \"" + pkt.script  + "\". It must have crashed.", player);
	}

	private void sendFilterOptions(EntityPlayerMP player, FilterScript fs) {
		ScriptingMod.DISPATCHER.sendTo(ScriptPacket.getPacket(PacketType.SETTINGS, fs.name, fs.getOptions()), player);
	}

	/**
	 * Calls the Filter script's method "run(player, world, selection, options)"
	 */
	private void runFilter(EntityPlayer player, Selection sel, Map<String, Object> options, FilterScript fs) {
		try {
			curScript = new Executing(fs, false);
			fs.getRun().call(context, fs.getScope(), fs.getScope(), new Object[] { 
				new ScriptPlayer(player), new ScriptWorld(player.worldObj), new ScriptSelection(sel, player), options
			});
		}
		catch(Exception e) {
			scriptCrash(fs, e);
		}
		finally {
			curScript = null;
		}
	}

}
