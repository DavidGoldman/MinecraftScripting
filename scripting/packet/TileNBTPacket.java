package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import scripting.network.ScriptPacketHandler;

public class TileNBTPacket extends ScriptPacket {

	public int x, y, z;
	public NBTTagCompound tag;

	@Override
	public ScriptPacket readData(Object... data) {
		x = (Integer) data[0];
		y = (Integer) data[1];
		x = (Integer) data[2];
		tag = (NBTTagCompound) data[3];
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException { 
		ByteBufOutputStream bos = new ByteBufOutputStream(to);
		bos.writeInt(x);
		bos.writeInt(y);
		bos.writeInt(z);
		writeNBT(tag, bos);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { 
		ByteBufInputStream bis = new ByteBufInputStream(from);
		x = bis.readInt();
		y = bis.readInt();
		z = bis.readInt();
		tag = readNBT(bis);
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleTileNBT(this, player);
	}

}