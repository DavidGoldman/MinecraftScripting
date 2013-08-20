package scripting.wrapper.settings;

import scripting.packet.ScriptPacket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingString extends Setting {
	
	public final String[] options;
	public String selected;
	
	protected SettingString(ByteArrayDataInput in) {
		super(in);
		
		this.options = ScriptPacket.readStringArray(in);
		this.selected = in.readUTF();
	}
	
	public SettingString(String display, String... options) throws IllegalArgumentException {
		super(display);
		
		this.options = options;
		if (options.length == 0)
			throw new IllegalArgumentException("Must have at least 1 option!");
		selected = options[0];
	}

	@Override
	public Object getValue() {
		return selected;
	}
	
	@Override
	protected void write(ByteArrayDataOutput out) {
		ScriptPacket.writeStringArray(options, out);
		out.writeUTF(selected);
	}
}
