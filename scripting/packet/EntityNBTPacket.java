package scripting.packet;

import java.io.IOException;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

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
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(entityID);
		try {
			NBTBase.writeNamedTag(tag, out);
		}
		catch(IOException e) { } //Should never happen
		return out.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		entityID = pkt.readInt();
		try {
			tag = (NBTTagCompound) NBTBase.readNamedTag(pkt);
		} catch (IOException e) { } //Should never happen
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleEntityNBT(this, player);
	}

}
