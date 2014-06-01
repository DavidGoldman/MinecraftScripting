package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SettingInt extends Setting {
	
	public int min, max;
	public int val;
	
	protected SettingInt() { }

	public SettingInt(String display, int val, int min, int max) {
		super(display);
	
		this.val = val;
		this.min = min;
		this.max = max;
	}
	
	public SettingInt(String display, int val) {
		this(display, val, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public Object getValue() {
		return val;
	}
	
	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		out.writeInt(val);
		out.writeInt(min);
		out.writeInt(max);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		val = in.readInt();
		min = in.readInt();
		max = in.readInt();
		return this;
	}

}
