package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;

public class TAG_Int_Array extends TAG_Base {
	
	public final NBTTagIntArray arr;
	
	public TAG_Int_Array(String name, int[] data) {
		super(new NBTTagIntArray(name, data));
		
		this.arr = (NBTTagIntArray)base;
	}
	
	public TAG_Int_Array(int[] data) {
		this("", data);
	}
	
	public TAG_Int_Array(NBTTagIntArray arr) {
		super(arr);
		
		this.arr = arr;
	}
	
	public TAG_Base copy() {
		return new TAG_Int_Array((NBTTagIntArray)arr.copy());
	}
	
	public int[] getIntArray() {
		return arr.intArray;
	}
	
	public void setIntArray(int[] intArray) {
		arr.intArray = intArray;
	}
}
