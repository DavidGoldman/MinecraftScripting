package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SettingFloat extends Setting {
	
	public float min, max;
	public float val;
	
	protected SettingFloat() { }

	public SettingFloat(String display, float val, float min, float max) {
		super(display);
	
		this.val = val;
		this.min = min;
		this.max = max;
	}
	
	public SettingFloat(String display, float val) {
		this(display, val, Float.MIN_VALUE, Float.MAX_VALUE);
	}

	@Override
	public Object getValue() {
		return val;
	}
	
	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		out.writeFloat(val);
		out.writeFloat(min);
		out.writeFloat(max);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		val = in.readFloat();
		min = in.readFloat();
		max = in.readFloat();
		return this;
	}

}
