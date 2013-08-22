package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;

public class TAG_Float extends TAG_Base {
	
	public final NBTTagFloat tag;
	
	public TAG_Float(String name, float data) {
		super(new NBTTagFloat(name, data));
		
		this.tag = (NBTTagFloat)base;
	}
	
	public TAG_Float(float data) {
		this("", data);
	}
	
	public TAG_Float(NBTTagFloat tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Float((NBTTagFloat)tag.copy());
	}
	
	public float getValue(){
		return tag.data;
	}
	
	public void setValue(float data) {
		tag.data = data;
	}
	
}
