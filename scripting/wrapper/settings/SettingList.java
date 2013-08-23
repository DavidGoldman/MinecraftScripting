package scripting.wrapper.settings;

import org.apache.commons.lang3.ArrayUtils;

import scripting.packet.ScriptPacket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingList extends Setting {
	
	public final String[] options;
	public String selected;
	
	protected SettingList(ByteArrayDataInput in) {
		super(in);
		
		this.options = ScriptPacket.readStringArray(in);
		this.selected = in.readUTF();
	}
	
	public SettingList(String display, String... options) throws IllegalArgumentException {
		super(display);
		
		this.options = options;
		if (options.length == 0)
			throw new IllegalArgumentException("Must have at least 1 option!");
		selected = options[0];
	}
	
	public SettingList(String display, String firstOption, String... options) {
		this(display, ArrayUtils.addAll(new String[] { firstOption }, options));
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
