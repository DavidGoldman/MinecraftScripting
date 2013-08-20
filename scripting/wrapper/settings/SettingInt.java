package scripting.wrapper.settings;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingInt extends Setting {
	
	public final int min, max;
	public int val;
	
	protected SettingInt(ByteArrayDataInput in) {
		super(in);
		
		this.val = in.readInt();
		this.min = in.readInt();
		this.max = in.readInt();
	}

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
	protected void write(ByteArrayDataOutput out) {
		out.writeInt(val);
		out.writeInt(min);
		out.writeInt(max);
	}

}
