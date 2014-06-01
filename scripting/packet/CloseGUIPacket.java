package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import scripting.network.ScriptPacketHandler;

public class CloseGUIPacket extends ScriptPacket {

	@Override
	public ScriptPacket readData(Object... data) {
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException { }

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { }

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleCloseGUI(this, player);
	}

}
