package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class TAG_Int extends TAG_Base {
	
	public final NBTTagInt tag;
	
	public TAG_Int(String name, int data) {
		super(new NBTTagInt(name, data));
		
		this.tag = (NBTTagInt)base;
	}
	
	public TAG_Int(int data) {
		this("", data);
	}

	public TAG_Int(NBTTagInt tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Int((NBTTagInt)tag.copy());
	}
	
	public int getValue(){
		return tag.data;
	}
	
	public void setValue(int data) {
		tag.data = data;
	}
}
