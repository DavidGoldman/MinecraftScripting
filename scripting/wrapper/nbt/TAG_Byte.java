package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagByte;

public class TAG_Byte extends TAG_Base {
	
	public final NBTTagByte tag;
	
	public TAG_Byte(byte data) {
		super(new NBTTagByte(data));
		
		this.tag = (NBTTagByte) base;
	}
	
	public TAG_Byte(NBTTagByte tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Byte((NBTTagByte)tag.copy());
	}
	
	public byte getValue(){
		return tag.func_150290_f();
	}
}
