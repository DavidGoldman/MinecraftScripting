package scripting.packet;

import scripting.core.ScriptCore.State;
import scripting.network.ScriptPacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class StatePacket extends ScriptPacket {
	
	public State[] states;

	@Override
	public ScriptPacket readData(Object... data) {
		states = (State[]) data[0];
		return this;
	}

	@Override
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(states.length);
		for (State s : states) {
			out.writeUTF(s.script);
			out.writeBoolean(s.running);
		}
		return out.toByteArray();
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		states = new State[pkt.readInt()];
		for (int i = 0; i < states.length; ++i)
			states[i] = new State(pkt.readUTF(), pkt.readBoolean());
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleState(this, player);
	}

}
