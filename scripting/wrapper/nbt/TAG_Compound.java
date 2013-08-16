package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class TAG_Compound extends TAG_Base {
	
	public final NBTTagCompound tag;

	public TAG_Compound() {
		super(new NBTTagCompound());
		
		this.tag = (NBTTagCompound)base;
	}

	public TAG_Compound(String name) {
		super(new NBTTagCompound(name));
		
		this.tag = (NBTTagCompound)base;
	}

	public TAG_Compound(NBTTagCompound tag) {
		super(tag);
		
		this.tag = tag;
	}

	public TAG_Base copy() {
		return new TAG_Compound((NBTTagCompound)tag.copy());
	}

	//-------------------------SETS-------------------------
	
	public void setTag(String name, TAG_Base tag) {
		this.tag.setTag(name, tag.base);
	}

	public void setByte(String name, byte data) {
		tag.setByte(name, data);
	}

	public void setShort(String name, short data) {
		tag.setShort(name, data);
	}

	public void setInteger(String name, int data) {
		tag.setInteger(name, data);
	}

	public void setLong(String name, long data) {
		tag.setLong(name, data);
	}

	public void setFloat(String name, float data) {
		tag.setFloat(name, data);
	}

	public void setDouble(String name, double data) {
		tag.setDouble(name, data);
	}

	public void setString(String name, String data) {
		tag.setString(name, data);
	}

	public void setByteArray(String name, byte[] data) {
		tag.setByteArray(name, data);
	}

	public void setIntArray(String name, int[] data) {
		tag.setIntArray(name, data);
	}
	
	public void setTagList(String name, TAG_List tag) {
		this.tag.setTag(name, tag.base);
	}

	public void setCompoundTag(String name, TAG_Compound tag) {
		this.tag.setCompoundTag(name, (NBTTagCompound) tag.base);
	}

	public void setBoolean(String name, boolean data) {
		tag.setBoolean(name, data);
	}
	
	//-------------------------GETS-------------------------
	
	public TAG_Base getTag(String name) {
		return TAG_Base.createFromNative(tag.getTag(name));
	}

	public byte getByte(String name) throws ReportedException {
		return tag.getByte(name);
	}

	public short getShort(String name) throws ReportedException {
		return tag.getShort(name);
	}

	public int getInteger(String name) throws ReportedException {
		return tag.getInteger(name);
	}

	public long getLong(String name) throws ReportedException {
		return tag.getLong(name);
	}

	public float getFloat(String name) throws ReportedException {
		return tag.getFloat(name);
	}

	public double getDouble(String name) throws ReportedException {
		return tag.getDouble(name);
	}

	public String getString(String name) throws ReportedException {
		return tag.getString(name);
	}

	public byte[] getByteArray(String name) throws ReportedException {
		return tag.getByteArray(name);
	}

	public int[] getIntArray(String name) throws ReportedException {
		return tag.getIntArray(name);
	}

	public TAG_Compound getCompoundTag(String name) throws ReportedException {
		return new TAG_Compound(tag.getCompoundTag(name));
	}
	
	public TAG_List getTagList(String name) throws ReportedException {
		return new TAG_List(tag.getTagList(name));
	}
	
	public boolean getBoolean(String name) throws ReportedException {
		return tag.getBoolean(name);
	}

	public boolean hasTag(String name) {
		return tag.hasKey(name);
	}

	public void removeTag(String name) {
		tag.removeTag(name);
	}

	public boolean hasNoTags() {
		return tag.hasNoTags();
	}

}
