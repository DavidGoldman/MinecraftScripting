package scripting.wrapper.settings;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import scripting.gui.settings.ISetting;

public class SettingString extends Setting {

	public String str;
	
	protected SettingString(ByteArrayDataInput in) {
		super(in);
		
		this.str = in.readUTF();
	}

	public SettingString(String display, String str) {
		super(display);

		this.str = str;
	}

	@Override
	public Object getValue() {
		return str;
	}

	@Override
	protected void write(ByteArrayDataOutput out) {
		out.writeUTF(str);
	}

}
