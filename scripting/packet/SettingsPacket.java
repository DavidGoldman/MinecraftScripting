package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import scripting.network.ScriptPacketHandler;
import scripting.wrapper.settings.Setting;

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
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException {
		ByteBufOutputStream bos = new ByteBufOutputStream(to);
		bos.writeUTF(script);
		bos.writeInt(settings.length);
		for (Setting s : settings)
			Setting.write(s, bos);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { 
		ByteBufInputStream bis = new ByteBufInputStream(from);
		script = bis.readUTF();
		settings = new Setting[bis.readInt()];
		for (int i = 0; i < settings.length; ++i)
			settings[i] = Setting.readSetting(bis);
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleSettings(this, player);
	}
}
