package scripting.wrapper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptBlock;
import scripting.wrapper.world.ScriptItem;

public class ScriptItemStack {
	
	public final ItemStack stack;

	private ScriptItemStack(ItemStack is) {
		this.stack = is;
	}
	
	public ScriptItemStack(ScriptItem item, int size, int damage) {
		this.stack = new ItemStack((item == null) ? null : item.item, size, damage);
	}
	
	public ScriptItemStack(ScriptBlock block, int size, int damage) {
		this.stack = new ItemStack((block == null) ? null : block.block, size, damage);
	}
	
	public ScriptItem getItem() {
		return (stack.getItem() == null) ? null : ScriptItem.fromItem(stack.getItem());
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
	
	public static ScriptItemStack fromItemStack(ItemStack stack) {
		return (stack == null) ? null : new ScriptItemStack(stack);
	}
	
}
