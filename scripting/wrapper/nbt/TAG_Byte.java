package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

public class TAG_Byte extends TAG_Base {
	
	public final NBTTagByte tag;
	
	public TAG_Byte(String name, byte data) {
		super(new NBTTagByte(name, data));
		
		this.tag = (NBTTagByte) base;
	}
	
	public TAG_Byte(byte data) {
		this("", data);
	}
	
	public TAG_Byte(NBTTagByte tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Byte((NBTTagByte)tag.copy());
	}
	
	public byte getValue(){
		return tag.data;
	}
	
	public void setValue(byte data) {
		tag.data = data;
	}

}
