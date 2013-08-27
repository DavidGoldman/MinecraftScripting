package scripting.wrapper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scripting.wrapper.nbt.TAG_Compound;

public class ScriptItemStack {
	
	public final ItemStack stack;

	public ScriptItemStack(ItemStack is) {
		this.stack = is;
	}
	
	public ScriptItemStack(int id, int size, int damage) {
		this.stack = new ItemStack(id, size, damage);
	}
	
	public int getItemID() {
		return stack.itemID;
	}
	
	public void setItemID(int itemID) {
		stack.itemID = itemID;
	}
	
	public int getStackSize() {
		return stack.stackSize;
	}
	
	public void setStackSize(int size) {
		stack.stackSize = size;
	}
	
	public int getItemDamage() {
		return stack.getItemDamage();
	}
	
	public void setItemDamage(int damage) {
		stack.setItemDamage(damage);
	}
	
	public boolean hasTagCompound() {
		return stack.hasTagCompound();
	}
	
	public TAG_Compound getTagCompound() {
		return (hasTagCompound()) ? new TAG_Compound(stack.getTagCompound()) : null;
	}
	
	public void setTagCompound(TAG_Compound tag) {
		stack.setTagCompound((tag == null) ? null : tag.tag);
	}
	
	public TAG_Compound writeToTag() {
		return new TAG_Compound(stack.writeToNBT(new NBTTagCompound()));
	}
	
	public void readFromTag(TAG_Compound tag) {
		stack.readFromNBT(tag.tag);
	}
	
}
