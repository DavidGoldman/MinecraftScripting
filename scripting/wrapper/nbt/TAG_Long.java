package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagLong;

public class TAG_Long extends TAG_Base {
	
	public final NBTTagLong tag;
	
	public TAG_Long(long data) {
		super(new NBTTagLong(data));
		
		this.tag = (NBTTagLong)base;
	}
	
	public TAG_Long(NBTTagLong tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Long((NBTTagLong)tag.copy());
	}
	
	public long getValue(){
		return tag.func_150291_c();
	}
}
