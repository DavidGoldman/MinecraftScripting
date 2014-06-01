package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import scripting.network.AbstractPacket;

import com.google.common.primitives.UnsignedBytes;

import cpw.mods.fml.common.network.ByteBufUtils;


/**
 * Core Packet class for the scripting mod.
 * See {@link cpw.mods.fml.common.network.FMLPacket} for more information.
 */

public abstract class ScriptPacket extends AbstractPacket {

	public static final String PACKET_ID = "scripting";

	public enum PacketType {

		SELECTION(SelectionPacket.class),
		HAS_SCRIPTS(HasScriptsPacket.class),
		STATE(StatePacket.class),
		SETTINGS(SettingsPacket.class),
		ENTITY_NBT(EntityNBTPacket.class),
		TILE_NBT(TileNBTPacket.class),
		CLOSE_GUI(CloseGUIPacket.class),
		REQUEST(RequestPacket.class);

		private Class<? extends ScriptPacket> packetType;

		private PacketType(Class<? extends ScriptPacket> cls) {
			this.packetType = cls;
		}

		private ScriptPacket make() {
			try {
				return packetType.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public static int ordinalForClass(Class<? extends ScriptPacket> cls) throws IllegalArgumentException {
			PacketType[] values = PacketType.values();
			for (int i = 0; i < values.length; ++i)
				if (values[i].packetType == cls)
					return i;
			throw new IllegalArgumentException("Unknown class " + cls);
		}
	}

	public abstract ScriptPacket readData(Object... data);

	//Packet creation
	public static ScriptPacket getRequestPacket(PacketType type) {
		return getPacket(PacketType.REQUEST, UnsignedBytes.checkedCast(type.ordinal()));
	}

	public static ScriptPacket getRequestPacket(PacketType type, String data) {
		return getPacket(PacketType.REQUEST, UnsignedBytes.checkedCast(type.ordinal()), data);
	}

	public static ScriptPacket getPacket(PacketType type, Object... data) {
		return type.make().readData(data);
	}

	//Helper methods for the PacketPipeline
	public static ScriptPacket readPacket(ChannelHandlerContext ctx, ByteBuf payload) throws IOException {
		int type = UnsignedBytes.toInt(payload.readByte());
		PacketType pType = PacketType.values()[type];
		ScriptPacket packet = pType.make();
		packet.decodeFrom(ctx, payload.slice());
		return packet;
	}

	public static void writePacket(ScriptPacket pkt, ChannelHandlerContext ctx, ByteBuf to) throws IOException {
		to.writeByte(UnsignedBytes.checkedCast(PacketType.ordinalForClass(pkt.getClass())));
		pkt.encodeInto(ctx, to);
	}


	//String ByteBuf utility methods
	public static void writeString(String str, ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, str);
	}

	public static String readString(ByteBuf buffer) {
		return ByteBufUtils.readUTF8String(buffer);
	}

	public static void writeStringArray(String[] arr, ByteBuf to) {
		to.writeInt(arr.length);
		for (String s : arr)
			writeString(s, to);
	}
	
	public static String[] readStringArray(ByteBuf from) {
		String[] arr = new String[from.readInt()];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = readString(from);
		return arr;
	}
	
	
	//Data(In/Out)put utility methods
	public static void writeNBT(NBTTagCompound compound, DataOutput out) throws IOException {
		CompressedStreamTools.write(compound, out);
	}
	
	public static NBTTagCompound readNBT(DataInput in) throws IOException {
		return CompressedStreamTools.read(in);
	}
	
	public static void writeItemStack(ItemStack stack, DataOutput out) throws IOException {
		writeNBT(stack.writeToNBT(new NBTTagCompound()), out);
	}
	
	public static ItemStack readItemStack(DataInput in) throws IOException {
		return ItemStack.loadItemStackFromNBT(readNBT(in));
	}
	
	public static void writeStringArray(String[] arr, DataOutput out) throws IOException {
		out.writeInt(arr.length);
		for (String s : arr)
			out.writeUTF(s);
	}
	
	public static String[] readStringArray(DataInput in) throws IOException {
		String[] arr = new String[in.readInt()];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = in.readUTF();
		return arr;
	}
}
