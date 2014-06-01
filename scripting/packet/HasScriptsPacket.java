package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import scripting.network.ScriptPacketHandler;

public class HasScriptsPacket extends ScriptPacket {
	
	public boolean hasScripts;

	@Override
	public ScriptPacket readData(Object... data) {
		if (data.length > 0)
			hasScripts = (Boolean) data[0];
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException { 
		to.writeBoolean(hasScripts);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { 
		hasScripts = from.readBoolean();
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleHasScripts(this, player);
	}

}
