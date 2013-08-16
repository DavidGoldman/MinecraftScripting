package scripting.packet;

import java.util.Arrays;

import net.minecraft.network.packet.Packet;
import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.UnsignedBytes;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * Core Packet class for the scripting mod.
 * See {@link cpw.mods.fml.common.network.FMLPacket} for more information.
 */

public abstract class ScriptPacket {
	
	public static final String PACKET_ID = "scripting";

	public enum PacketType {
		
		SELECTION(SelectionPacket.class),
		HAS_SCRIPTS(HasScriptsPacket.class),
		STATE(StatePacket.class),
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
	}
	
	public abstract ScriptPacket readData(Object... data);
	public abstract byte[] generatePacket();
	public abstract ScriptPacket readPacket(ByteArrayDataInput pkt);
	public abstract void execute(ScriptPacketHandler handler, Player player);
	
	public static byte[] makePacket(PacketType type, Object... data) {
		byte[] packetData = type.make().readData(data).generatePacket();
		return Bytes.concat(new byte[] { UnsignedBytes.checkedCast(type.ordinal()) }, packetData);
	}
	
	public static Packet getRequestPacket(PacketType type) {
		return getPacket(PacketType.REQUEST, UnsignedBytes.checkedCast(type.ordinal()));
	}
	
	public static Packet getRequestPacket(PacketType type, String info) {
		return getPacket(PacketType.REQUEST, UnsignedBytes.checkedCast(type.ordinal()), info);
	}
	
	public static Packet getPacket(PacketType type, Object... data) {
		return PacketDispatcher.getPacket(PACKET_ID, makePacket(type, data));
	}
	
	public static ScriptPacket readPacket(byte[] payload) {
		int type = UnsignedBytes.toInt(payload[0]);
		PacketType pType = PacketType.values()[type];
		return pType.make().readPacket(ByteStreams.newDataInput(Arrays.copyOfRange(payload, 1, payload.length)));
	}

	public static void writeStringArray(String[] arr, ByteArrayDataOutput output) {
		output.writeInt(arr.length);
		for (String s : arr)
			output.writeUTF(s);
	}

	public static String[] readStringArray(ByteArrayDataInput input) {
		String[] arr = new String[input.readInt()];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = input.readUTF();
		return arr;
	}

}
