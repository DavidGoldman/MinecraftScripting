package scripting.packet;

import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.UnsignedBytes;

import cpw.mods.fml.common.network.Player;

public class RequestPacket extends ScriptPacket {

	private byte request;
	private String info;
	
	@Override
	public ScriptPacket readData(Object... data) {
		request = (Byte) data[0];
		if (data.length > 1)
			info = (String) data[1];
		return this;
	}

	@Override
	public byte[] generatePacket() {
		ByteArrayDataOutput dat = ByteStreams.newDataOutput();
		dat.writeByte(request);
		dat.writeUTF(info != null ? info : "");
		return dat.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		request = pkt.readByte();
		info = pkt.readUTF();
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleRequest(PacketType.values()[UnsignedBytes.toInt(request)], info, player);
	}

}
