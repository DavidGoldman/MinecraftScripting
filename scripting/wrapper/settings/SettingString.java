package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SettingString extends Setting {

	public String str;
	
	protected SettingString() { }

	public SettingString(String display, String str) {
		super(display);

		this.str = str;
	}

	@Override
	public Object getValue() {
		return str;
	}
	
	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		out.writeUTF(str);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		str = in.readUTF();
		return this;
	}

}
