package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

public class TAG_String extends TAG_Base {
	
	public final NBTTagString str;
	
	public TAG_String(String name, String data) {
		super(new NBTTagString(name, data));
		
		this.str = (NBTTagString)base;
	}
	
	public TAG_String(String data) {
		this("", data);
	}
	
	public TAG_String(NBTTagString str) {
		super(str);
		
		this.str = str;
	}
	
	public TAG_Base copy() {
		return new TAG_String((NBTTagString)str.copy());
	}
	
	public String getValue(){
		return str.data;
	}
	
	public void setValue(String data) {
		str.data = data;
	}
}
