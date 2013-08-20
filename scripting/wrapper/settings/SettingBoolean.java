package scripting.wrapper.settings;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingBoolean extends Setting {

	public boolean enabled;
	
	protected SettingBoolean(ByteArrayDataInput in) {
		super(in);
		
		this.enabled = in.readBoolean();
	}

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
	protected void write(ByteArrayDataOutput out) {
		out.writeBoolean(enabled);
	}

}
