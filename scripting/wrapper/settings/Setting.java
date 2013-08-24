package scripting.wrapper.settings;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public abstract class Setting {
	
	public final String display;
	
	protected Setting(ByteArrayDataInput in) {
		display = in.readUTF();
	}
	
	public Setting(String display) {
		this.display = display;
	}
	
	public abstract Object getValue();
	
	protected abstract void write(ByteArrayDataOutput out);
	
	public static Setting read(ByteArrayDataInput in) {
		switch(in.readByte()) {
		case 0: return new SettingBoolean(in);
		case 1: return new SettingInt(in);
		case 2: return new SettingFloat(in);
		case 3: return new SettingString(in);
		case 4: return new SettingList(in);
		case 5: return new SettingBlock(in);
		default: return null;
		}
	}
	
	public static void write(Setting setting, ByteArrayDataOutput out) {
		if (setting instanceof SettingBoolean) {
			out.write(0);
			out.writeUTF(setting.display);
			setting.write(out);
		}
		if (setting instanceof SettingInt) {
			out.write(1);
			out.writeUTF(setting.display);
			setting.write(out);
		}
		if (setting instanceof SettingFloat) {
			out.write(2);
			out.writeUTF(setting.display);
			setting.write(out);
		}
		if (setting instanceof SettingString) {
			out.write(3);
			out.writeUTF(setting.display);
			setting.write(out);
		}
		if (setting instanceof SettingList) {
			out.write(4);
			out.writeUTF(setting.display);
			setting.write(out);
		}
		if (setting instanceof SettingBlock) {
			out.write(5);
			out.writeUTF(setting.display);
			setting.write(out);
		}
	}
	
	/**
	 * Helper method for filters
	 */
	public static Setting[] toArray(Setting... settings) {
		return settings;
	}
	
}
