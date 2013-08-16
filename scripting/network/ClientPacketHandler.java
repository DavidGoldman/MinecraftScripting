package scripting.network;

import net.minecraft.client.Minecraft;
import scripting.ScriptingMod;
import scripting.Selection;
import scripting.gui.MainScreen;
import scripting.gui.ServerMenu;
import scripting.packet.CloseGUIPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.StatePacket;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler extends ScriptPacketHandler {
	
	protected Minecraft mc = Minecraft.getMinecraft();

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
		if (mc.currentScreen instanceof MainScreen || mc.currentScreen instanceof ServerMenu)
			mc.displayGuiScreen(null);
	}

}
