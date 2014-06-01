package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagDouble;

public class TAG_Double extends TAG_Base {
	
	public final NBTTagDouble tag;

	public TAG_Double(double data) {
		super(new NBTTagDouble(data));
		
		this.tag = (NBTTagDouble)base;
	}
	
	public TAG_Double(NBTTagDouble tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Double((NBTTagDouble)tag.copy());
	}
	
	public double getValue(){
		return tag.func_150286_g();
	}
}
