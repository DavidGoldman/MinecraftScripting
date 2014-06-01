package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import scripting.network.ScriptPacketHandler;

public class EntityNBTPacket extends ScriptPacket {

	public int entityID;
	public NBTTagCompound tag;

	@Override
	public ScriptPacket readData(Object... data) {
		entityID = (Integer) data[0];
		tag = (NBTTagCompound) data[1];
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException {
		ByteBufOutputStream bos = new ByteBufOutputStream(to);
		bos.writeInt(entityID);
		writeNBT(tag, bos);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException {
		ByteBufInputStream bis = new ByteBufInputStream(from);
		entityID = bis.readInt();
		tag = readNBT(bis);
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleEntityNBT(this, player);
	}

}
