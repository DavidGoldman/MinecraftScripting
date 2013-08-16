package scripting.wrapper;

import scripting.wrapper.nbt.TAG_Compound;
import net.minecraft.item.ItemStack;

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
}
