package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;

public class TAG_Byte_Array extends TAG_Base {
	
	public final NBTTagByteArray arr;

	public TAG_Byte_Array(String name, byte[] data) {
		super(new NBTTagByteArray(name, data));
		
		this.arr = (NBTTagByteArray)base;
	}
	
	public TAG_Byte_Array(byte[] data) {
		this("", data);
	}
	
	public TAG_Byte_Array(NBTTagByteArray arr) {
		super(arr);
		
		this.arr = arr;
	}
	
	public TAG_Base copy() {
		return new TAG_Byte_Array((NBTTagByteArray)arr.copy());
	}
	
	public byte[] getValue() {
		return arr.byteArray;
	}
	
	public void setValue(byte[] byteArray) {
		arr.byteArray = byteArray;
	}

}
