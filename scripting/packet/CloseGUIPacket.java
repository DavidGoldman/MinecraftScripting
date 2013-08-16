package scripting.packet;

import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.Player;

public class CloseGUIPacket extends ScriptPacket {

	@Override
	public ScriptPacket readData(Object... data) {
		return this;
	}

	@Override
	public byte[] generatePacket() {
		return new byte[0];
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleCloseGUI(this, player);
	}

}
