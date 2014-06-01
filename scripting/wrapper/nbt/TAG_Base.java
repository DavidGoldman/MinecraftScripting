package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

/**
 * Base class for NBTTag wrapping.
 * Note that there is no wrapper for NBTTagEnd, as it is only used for tag IO.
 *
 */
public abstract class TAG_Base {

	protected final NBTBase base;

	public TAG_Base(NBTBase base) {
		this.base = base;
	}
	
	public abstract TAG_Base copy();
	
	public String toString() {
		return base.toString();
	}
	
	public int hashCode() {
		return base.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof TAG_Base)
			return ((TAG_Base)obj).base.equals(base);
		return false;
	}
	
	/**
	 * Called to create a script visible NBT Tag.
	 */
	public static TAG_Base createFromNative(NBTBase base) {
		if (base instanceof NBTTagByte)
			return new TAG_Byte((NBTTagByte)base);
		if (base instanceof NBTTagByteArray)
			return new TAG_Byte_Array((NBTTagByteArray)base);
		if (base instanceof NBTTagCompound)
			return new TAG_Compound((NBTTagCompound)base);
		if (base instanceof NBTTagDouble)
			return new TAG_Double((NBTTagDouble)base);
		if (base instanceof NBTTagFloat)
			return new TAG_Float((NBTTagFloat)base);
		if (base instanceof NBTTagInt)
			return new TAG_Int((NBTTagInt)base);
		if (base instanceof NBTTagIntArray)
			return new TAG_Int_Array((NBTTagIntArray)base);
		if (base instanceof NBTTagList)
			return new TAG_List((NBTTagList)base);
		if (base instanceof NBTTagLong)
			return new TAG_Long((NBTTagLong)base);
		if (base instanceof NBTTagShort)
			return new TAG_Short((NBTTagShort)base);
		if (base instanceof NBTTagString)
			return new TAG_String((NBTTagString)base);
		return null;
		
	}
}
