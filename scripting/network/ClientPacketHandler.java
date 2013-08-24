package scripting.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import scripting.ScriptingMod;
import scripting.Selection;
import scripting.gui.MainScreen;
import scripting.gui.ScriptScreen;
import scripting.gui.ServerMenu;
import scripting.gui.settings.PopupDropdown;
import scripting.gui.settings.SettingsScreen;
import scripting.gui.settings.items.ItemPopup;
import scripting.packet.CloseGUIPacket;
import scripting.packet.EntityNBTPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.SettingsPacket;
import scripting.packet.StatePacket;
import scripting.packet.TileNBTPacket;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler extends ScriptPacketHandler {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	protected boolean hasPermission(Player player) {
		return true;
	}

	@Override
	public void handleSelection(SelectionPacket pkt, Player player) {
		Selection s =  pkt.getSelection(player);
		ScriptingMod.proxy.setSelection(s);
	}

	@Override
	public void handleHasScripts(HasScriptsPacket pkt, Player player) {
		if (mc.currentScreen instanceof MainScreen)
			((MainScreen)mc.currentScreen).setServerScripts(pkt.hasScripts);
	}

	@Override
	public void handleState(StatePacket pkt, Player player) {
		if (mc.currentScreen instanceof MainScreen)
			mc.displayGuiScreen(new ServerMenu(pkt, mc.currentScreen));
		if (mc.currentScreen instanceof ServerMenu)
			((ServerMenu)mc.currentScreen).update(pkt);
	}

	@Override
	public void handleRequest(PacketType type, String info, Player player) { }

	@Override
	public void handleCloseGUI(CloseGUIPacket pkt, Player player) {
		if (mc.currentScreen instanceof ScriptScreen || mc.currentScreen instanceof PopupDropdown || mc.currentScreen instanceof ItemPopup)
			mc.displayGuiScreen(null);
	}

	@Override
	public void handleEntityNBT(EntityNBTPacket pkt, Player player) {
		if (mc.theWorld != null) {
			Entity e = mc.theWorld.getEntityByID(pkt.entityID);
			if (e != null)
				e.readFromNBT(pkt.tag);
		}
	}

	@Override
	public void handleTileNBT(TileNBTPacket pkt, Player player) {
		if (mc.theWorld != null) {
			TileEntity t = mc.theWorld.getBlockTileEntity(pkt.x, pkt.y, pkt.z);
			if (t != null)
				t.readFromNBT(pkt.tag);
		}
		
	}

	@Override
	public void handleSettings(SettingsPacket pkt, Player player) {
		if (mc.currentScreen == null)
			mc.displayGuiScreen(new SettingsScreen(pkt, mc.currentScreen));
	}

}
