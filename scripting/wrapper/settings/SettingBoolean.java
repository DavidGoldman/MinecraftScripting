package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SettingBoolean extends Setting {

	public boolean enabled;
	
	protected SettingBoolean() { }

	public SettingBoolean(String display, boolean enabled) {
		super(display);

		this.enabled = enabled;
	}

	public SettingBoolean(String display) {
		this(display, false);
	}

	@Override
	public Object getValue() {
		return enabled; 
	}
	
	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		out.writeBoolean(enabled);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		enabled = in.readBoolean();
		return this;
	}
	
}
