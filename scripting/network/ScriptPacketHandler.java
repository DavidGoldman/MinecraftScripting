package scripting.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import scripting.packet.CloseGUIPacket;
import scripting.packet.EntityNBTPacket;
import scripting.packet.HasScriptsPacket;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SelectionPacket;
import scripting.packet.SettingsPacket;
import scripting.packet.StatePacket;
import scripting.packet.TileNBTPacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public abstract class ScriptPacketHandler implements IPacketHandler {
	
	public abstract void handleSelection(SelectionPacket pkt, Player player);
	public abstract void handleHasScripts(HasScriptsPacket pkt, Player player);
	public abstract void handleState(StatePacket pkt, Player player);
	
	public abstract void handleSettings(SettingsPacket pkt, Player player);
	
	public abstract void handleEntityNBT(EntityNBTPacket pkt, Player player);
	public abstract void handleTileNBT(TileNBTPacket pkt, Player player);
	
	public abstract void handleCloseGUI(CloseGUIPacket pkt, Player player);
	public abstract void handleRequest(PacketType type, String info, Player player);

	@Override
	public final void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ScriptPacket.readPacket(packet.data).execute(this, player);
	}
}
