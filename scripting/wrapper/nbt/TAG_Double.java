package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagDouble;

public class TAG_Double extends TAG_Base {
	
	public final NBTTagDouble tag;

	public TAG_Double(String name, double data) {
		super(new NBTTagDouble(name, data));
		
		this.tag = (NBTTagDouble)base;
	}
	
	public TAG_Double(double data) {
		this("", data);
	}
	
	public TAG_Double(NBTTagDouble tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Double((NBTTagDouble)tag.copy());
	}
	
	public double getDouble(){
		return tag.data;
	}
	
	public void setDouble(double data) {
		tag.data = data;
	}
}
