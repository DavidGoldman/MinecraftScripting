package scripting.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import scripting.ScriptingMod;
import scripting.Selection;
import scripting.gui.MainScreen;
import scripting.gui.ScriptOverlay;
import scripting.gui.ScriptScreen;
import scripting.gui.ServerMenu;
import scripting.gui.settings.SettingsScreen;
import scripting.packet.CloseGUIPacket;
import scripting.packet.EntityNBTPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.SettingsPacket;
import scripting.packet.StatePacket;
import scripting.packet.TileNBTPacket;

public class ClientPacketHandler extends ScriptPacketHandler {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	protected boolean hasPermission(EntityPlayer player) {
		return true;
	}

	@Override
	public void handleSelection(SelectionPacket pkt, EntityPlayer player) {
		Selection s =  pkt.getSelection(player);
		ScriptingMod.proxy.setSelection(s);
	}

	@Override
	public void handleHasScripts(HasScriptsPacket pkt, EntityPlayer player) {
		if (mc.currentScreen instanceof MainScreen)
			((MainScreen)mc.currentScreen).setServerScripts(pkt.hasScripts);
	}

	@Override
	public void handleState(StatePacket pkt, EntityPlayer player) {
		if (mc.currentScreen instanceof MainScreen)
			mc.displayGuiScreen(new ServerMenu(pkt, mc.currentScreen));
		if (mc.currentScreen instanceof ServerMenu)
			((ServerMenu)mc.currentScreen).update(pkt);
	}

	@Override
	public void handleRequest(PacketType type, String info, EntityPlayer player) { }

	@Override
	public void handleCloseGUI(CloseGUIPacket pkt, EntityPlayer player) {
		if (mc.currentScreen instanceof ScriptScreen || mc.currentScreen instanceof ScriptOverlay)
			mc.displayGuiScreen(null);
	}

	@Override
	public void handleEntityNBT(EntityNBTPacket pkt, EntityPlayer player) {
		if (mc.theWorld != null) {
			Entity e = mc.theWorld.getEntityByID(pkt.entityID);
			if (e != null)
				e.readFromNBT(pkt.tag);
		}
	}

	@Override
	public void handleTileNBT(TileNBTPacket pkt, EntityPlayer player) {
		if (mc.theWorld != null) {
			TileEntity t = mc.theWorld.getTileEntity(pkt.x, pkt.y, pkt.z);
			if (t != null)
				t.readFromNBT(pkt.tag);
		}
	}

	@Override
	public void handleSettings(SettingsPacket pkt, EntityPlayer player) {
		if (mc.currentScreen == null)
			mc.displayGuiScreen(new SettingsScreen(pkt, mc.currentScreen));
	}

}
