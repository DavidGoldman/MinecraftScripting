package scripting.wrapper.settings;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingFloat extends Setting {
	
	public final float min, max;
	public float val;
	
	protected SettingFloat(ByteArrayDataInput in) {
		super(in);
		
		this.val = in.readFloat();
		this.min = in.readFloat();
		this.max = in.readFloat();
	}

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
	protected void write(ByteArrayDataOutput out) {
		out.writeFloat(val);
		out.writeFloat(min);
		out.writeFloat(max);
	}

}
