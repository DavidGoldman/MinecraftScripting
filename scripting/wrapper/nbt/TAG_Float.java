package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagFloat;

public class TAG_Float extends TAG_Base {
	
	public final NBTTagFloat tag;
	
	public TAG_Float(float data) {
		super(new NBTTagFloat(data));
		
		this.tag = (NBTTagFloat)base;
	}
	
	public TAG_Float(NBTTagFloat tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Float((NBTTagFloat)tag.copy());
	}
	
	public float getValue(){
		return tag.func_150288_h();
	}
}
