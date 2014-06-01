package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import scripting.network.ScriptPacketHandler;

import com.google.common.primitives.UnsignedBytes;

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
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException { 
		to.writeByte(request);
		writeString(info != null ? info : "", to);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { 
		request = from.readByte();
		info = readString(from);
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleRequest(PacketType.values()[UnsignedBytes.toInt(request)], info, player);
	}

}
