package scripting.network;

import net.minecraft.entity.player.EntityPlayerMP;
import scripting.ScriptingMod;
import scripting.core.ScriptCore.State;
import scripting.core.ServerCore;
import scripting.packet.CloseGUIPacket;
import scripting.packet.EntityNBTPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.SettingsPacket;
import scripting.packet.StatePacket;
import scripting.packet.TileNBTPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler extends ScriptPacketHandler {

	@Override
	public void handleSelection(SelectionPacket pkt, Player player) { }
	@Override
	public void handleCloseGUI(CloseGUIPacket pkt, Player player) { }
	
	@Override
	public void handleEntityNBT(EntityNBTPacket pkt, Player player) { }
	@Override
	public void handleTileNBT(TileNBTPacket pkt, Player player) { }
	

	@Override
	public void handleHasScripts(HasScriptsPacket pkt, Player player) {
		boolean hasScripts = ScriptingMod.instance.getServerCore().hasScripts();
		PacketDispatcher.sendPacketToPlayer(ScriptPacket.getPacket(PacketType.HAS_SCRIPTS, hasScripts), player);
	}
	
	//Toggle script
	@Override
	public void handleState(StatePacket pkt, Player player) { 
		ServerCore core = ScriptingMod.instance.getServerCore();
		State state = pkt.states[0];
		boolean wasRunning = state.running;
		if (wasRunning && core.isScriptRunning(state.script) || !wasRunning && !core.isScriptRunning(state.script))
			core.toggleScript(state.script);
		State[] arr =  core.getBasicScripts().toArray(new State[0]);
		PacketDispatcher.sendPacketToPlayer(ScriptPacket.getPacket(PacketType.STATE, (Object)arr), player);
	}
	

	@Override
	public void handleRequest(PacketType type, String dat, Player player) {
		switch(type){
		case STATE:
			State[] arr =  ScriptingMod.instance.getServerCore().getBasicScripts().toArray(new State[0]);
			PacketDispatcher.sendPacketToPlayer(ScriptPacket.getPacket(PacketType.STATE, (Object)arr), player);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void handleSettings(SettingsPacket pkt, Player player) {
		ScriptingMod.instance.getServerCore().runFilter((EntityPlayerMP)player, pkt);
	}

}
