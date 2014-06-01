package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagShort;

public class TAG_Short extends TAG_Base {

	public final NBTTagShort tag;
	
	public TAG_Short(short data) {
		super(new NBTTagShort( data));
		
		this.tag = (NBTTagShort)base;
	}

	public TAG_Short(NBTTagShort tag) {
		super(tag);
		
		this.tag = tag;
	}
	
	public TAG_Base copy() {
		return new TAG_Short((NBTTagShort)tag.copy());
	}
	
	public short getValue(){
		return tag.func_150289_e();
	}
}
