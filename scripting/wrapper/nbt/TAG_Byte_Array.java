package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagByteArray;

public class TAG_Byte_Array extends TAG_Base {
	
	public final NBTTagByteArray arr;

	public TAG_Byte_Array(byte[] data) {
		super(new NBTTagByteArray(data));
		
		this.arr = (NBTTagByteArray)base;
	}

	public TAG_Byte_Array(NBTTagByteArray arr) {
		super(arr);
		
		this.arr = arr;
	}
	
	public TAG_Base copy() {
		return new TAG_Byte_Array((NBTTagByteArray)arr.copy());
	}
	
	public byte[] getValue() {
		return arr.func_150292_c();
	}
}
