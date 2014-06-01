package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

import scripting.packet.ScriptPacket;

public class SettingList extends Setting {
	
	public String[] options;
	public String selected;
	
	protected SettingList() { }
	
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
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		ScriptPacket.writeStringArray(options, out);
		out.writeUTF(selected);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		options = ScriptPacket.readStringArray(in);
		selected = in.readUTF();
		return this;
	}
	
}
