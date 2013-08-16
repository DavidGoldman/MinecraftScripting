package scripting.packet;

import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class HasScriptsPacket extends ScriptPacket {
	
	public boolean hasScripts;

	@Override
	public ScriptPacket readData(Object... data) {
		if (data.length > 0)
			hasScripts = (Boolean) data[0];
		return this;
	}

	@Override
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeBoolean(hasScripts);
		return out.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		hasScripts = pkt.readBoolean();
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleHasScripts(this, player);
	}

}
