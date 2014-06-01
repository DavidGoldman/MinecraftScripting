package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagIntArray;

public class TAG_Int_Array extends TAG_Base {
	
	public final NBTTagIntArray arr;
	
	public TAG_Int_Array(int[] data) {
		super(new NBTTagIntArray(data));
		
		this.arr = (NBTTagIntArray)base;
	}
	
	public TAG_Int_Array(NBTTagIntArray arr) {
		super(arr);
		
		this.arr = arr;
	}
	
	public TAG_Base copy() {
		return new TAG_Int_Array((NBTTagIntArray)arr.copy());
	}
	
	public int[] getValue() {
		return arr.func_150302_c();
	}
}
