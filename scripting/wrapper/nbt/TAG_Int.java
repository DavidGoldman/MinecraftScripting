package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagInt;

public class TAG_Int extends TAG_Base {
	
	public final NBTTagInt tag;
	
	public TAG_Int(int data) {
		super(new NBTTagInt(data));
		
		this.tag = (NBTTagInt)base;
	}

	public TAG_Int(NBTTagInt tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Int((NBTTagInt)tag.copy());
	}
	
	public int getValue(){
		return tag.func_150287_d();
	}
}
