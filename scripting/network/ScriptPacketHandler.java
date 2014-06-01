package scripting.network;

import net.minecraft.entity.player.EntityPlayer;
import scripting.packet.CloseGUIPacket;
import scripting.packet.EntityNBTPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.SettingsPacket;
import scripting.packet.StatePacket;
import scripting.packet.TileNBTPacket;

public abstract class ScriptPacketHandler {

	public abstract void handleSelection(SelectionPacket pkt, EntityPlayer player);
	public abstract void handleHasScripts(HasScriptsPacket pkt, EntityPlayer player);
	public abstract void handleState(StatePacket pkt, EntityPlayer player);

	public abstract void handleSettings(SettingsPacket pkt, EntityPlayer player);

	public abstract void handleEntityNBT(EntityNBTPacket pkt, EntityPlayer player);
	public abstract void handleTileNBT(TileNBTPacket pkt, EntityPlayer player);

	public abstract void handleCloseGUI(CloseGUIPacket pkt, EntityPlayer player);
	public abstract void handleRequest(PacketType type, String info, EntityPlayer player);

	protected abstract boolean hasPermission(EntityPlayer player);

	public final void handlePacket(ScriptPacket packet, EntityPlayer player) {
		if (hasPermission(player))
			packet.execute(this, player);
	}
}
