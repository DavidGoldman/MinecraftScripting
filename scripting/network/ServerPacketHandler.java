package scripting.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import scripting.Config;
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

public class ServerPacketHandler extends ScriptPacketHandler {
	
	@Override
	protected boolean hasPermission(EntityPlayer player) {
		return Config.hasPermission((EntityPlayer)player);
	}

	@Override
	public void handleSelection(SelectionPacket pkt, EntityPlayer player) { }
	@Override
	public void handleCloseGUI(CloseGUIPacket pkt, EntityPlayer player) { }
	
	@Override
	public void handleEntityNBT(EntityNBTPacket pkt, EntityPlayer player) { }
	@Override
	public void handleTileNBT(TileNBTPacket pkt, EntityPlayer player) { }
	

	@Override
	public void handleHasScripts(HasScriptsPacket pkt, EntityPlayer player) {
		boolean hasScripts = ScriptingMod.instance.getServerCore().hasScripts();
		ScriptingMod.DISPATCHER.sendTo(ScriptPacket.getPacket(PacketType.HAS_SCRIPTS, hasScripts), (EntityPlayerMP)player);
	}
	
	//Toggle script
	@Override
	public void handleState(StatePacket pkt, EntityPlayer player) { 
		ServerCore core = ScriptingMod.instance.getServerCore();
		State state = pkt.states[0];
		boolean wasRunning = state.running;
		if (wasRunning && core.isScriptRunning(state.script) || !wasRunning && !core.isScriptRunning(state.script))
			core.toggleScript(state.script);
		State[] arr =  core.getBasicScripts().toArray(new State[0]);
		ScriptingMod.DISPATCHER.sendTo(ScriptPacket.getPacket(PacketType.STATE, (Object)arr), (EntityPlayerMP)player);
	}
	

	@Override
	public void handleRequest(PacketType type, String dat, EntityPlayer player) {
		switch(type){
		case STATE:
			State[] arr =  ScriptingMod.instance.getServerCore().getBasicScripts().toArray(new State[0]);
			ScriptingMod.DISPATCHER.sendTo(ScriptPacket.getPacket(PacketType.STATE, (Object)arr), (EntityPlayerMP)player);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void handleSettings(SettingsPacket pkt, EntityPlayer player) {
		ScriptingMod.instance.getServerCore().runFilter((EntityPlayerMP)player, pkt);
	}

}
