package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
 * TODO Add ID map
 */
public abstract class Setting {
	
	public String display;
	
	protected Setting() { }
	
	public Setting(String display) {
		this.display = display;
	}
	
	public abstract Object getValue();
	
	protected void write(DataOutput out) throws IOException {
		out.writeUTF(display);
	}
	
	protected Setting read(DataInput in) throws IOException {
		display = in.readUTF();
		return this;
	}
	
	public static Setting readSetting(DataInput in) throws IOException {
		switch(in.readByte()) {
		case 0: return new SettingBoolean().read(in);
		case 1: return new SettingInt().read(in);
		case 2: return new SettingFloat().read(in);
		case 3: return new SettingString().read(in);
		case 4: return new SettingList().read(in);
		case 5: return new SettingBlock().read(in);
		case 6: return new SettingItem().read(in);
		default: return null;
		}
	}
	
	public static void write(Setting setting, DataOutput out) throws IOException {
		if (setting == null) 
			return;
		if (setting instanceof SettingBoolean) 
			out.write(0);
		if (setting instanceof SettingInt) 
			out.write(1);
		if (setting instanceof SettingFloat) 
			out.write(2);
		if (setting instanceof SettingString) 
			out.write(3);
		if (setting instanceof SettingList)
			out.write(4);
		if (setting instanceof SettingBlock)
			out.write(5);
		if (setting instanceof SettingItem)
			out.write(6);
		setting.write(out);
	}
	
	/**
	 * Helper method for filters
	 */
	public static Setting[] toArray(Setting... settings) {
		return settings;
	}
	
}
