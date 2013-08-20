package scripting.packet;

import scripting.network.ScriptPacketHandler;
import scripting.wrapper.settings.Setting;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class SettingsPacket extends ScriptPacket {

	public String script;
	public Setting[] settings;

	@Override
	public ScriptPacket readData(Object... data) {
		script = (String) data[0];
		settings = (Setting[]) data[1];
		return this;
	}

	@Override
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(script);
		out.writeInt(settings.length);
		for (Setting s : settings)
			Setting.write(s, out);
		return out.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		script = pkt.readUTF();
		settings = new Setting[pkt.readInt()];
		for (int i = 0; i < settings.length; ++i)
			settings[i] = Setting.read(pkt);
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleSettings(this, player);
	}
}
