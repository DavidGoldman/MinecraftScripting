package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagShort;

public class TAG_Short extends TAG_Base {

	public final NBTTagShort tag;
	
	public TAG_Short(String name, short data) {
		super(new NBTTagShort(name, data));
		
		this.tag = (NBTTagShort)base;
	}
	
	public TAG_Short(short data) {
		this("", data);
	}
	
	public TAG_Short(NBTTagShort tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Short((NBTTagShort)tag.copy());
	}
	
	public short getShort(){
		return tag.data;
	}
	
	public void setShort(short data) {
		tag.data = data;
	}
}
