package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagLong;

public class TAG_Long extends TAG_Base {
	
	public final NBTTagLong tag;
	
	public TAG_Long(String name, long data) {
		super(new NBTTagLong(name, data));
		
		this.tag = (NBTTagLong)base;
	}
	
	public TAG_Long(long data) {
		this("", data);
	}
	
	public TAG_Long(NBTTagLong tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Long((NBTTagLong)tag.copy());
	}
	
	public long getLong(){
		return tag.data;
	}
	
	public void setLong(long data) {
		tag.data = data;
	}
}
