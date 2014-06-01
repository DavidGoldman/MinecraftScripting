package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import scripting.core.ScriptCore.State;
import scripting.network.ScriptPacketHandler;

public class StatePacket extends ScriptPacket {
	
	public State[] states;

	@Override
	public ScriptPacket readData(Object... data) {
		states = (State[]) data[0];
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException {
		to.writeInt(states.length);
		for (State s : states) {
			writeString(s.script, to);
			to.writeBoolean(s.running);
		}
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException { 
		states = new State[from.readInt()];
		for (int i = 0; i < states.length; ++i)
			states[i] = new State(readString(from), from.readBoolean());
	}
	
	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleState(this, player);
	}

}
