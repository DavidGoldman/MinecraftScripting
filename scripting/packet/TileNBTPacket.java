package scripting.packet;

import java.io.IOException;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

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
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		try {
			NBTBase.writeNamedTag(tag, out);
		}
		catch(IOException e) { } //Should never happen
		return out.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		x = pkt.readInt();
		y = pkt.readInt();
		z = pkt.readInt();
		try {
			tag = (NBTTagCompound) NBTBase.readNamedTag(pkt);
		} catch (IOException e) { } //Should never happen
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleTileNBT(this, player);
	}

}