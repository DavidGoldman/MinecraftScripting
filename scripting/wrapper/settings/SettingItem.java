package scripting.wrapper.settings;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import scripting.wrapper.ScriptItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingItem extends Setting {

	public ItemStack selected;
	public ItemStack[] options;
	private final boolean writeOptions;

	protected SettingItem(ByteArrayDataInput in) {
		super(in);

		selected = readStack(in);
		if (in.readBoolean()) {//has options
			options = new ItemStack[in.readInt()];
			for (int i = 0; i < options.length; ++i)
				options[i] = readStack(in);
		}
		writeOptions = false;
	}

	public SettingItem(String display, ScriptItemStack selected, ScriptItemStack... options) {
		super(display);

		this.selected = selected.stack;
		this.options = new ItemStack[options.length];
		for (int i = 0; i < options.length; ++i) {
			if (options[i].stack.getItem() == null)
				throw new IllegalArgumentException("Must be a valid item");
			this.options[i] = options[i].stack;
		}
		writeOptions = options.length > 0;
	}

	@Override
	public Object getValue() {
		return new ScriptItemStack(selected);
	}

	@Override
	protected void write(ByteArrayDataOutput out) {
		writeStack(selected, out);
		out.writeBoolean(writeOptions);
		if (writeOptions) {
			out.writeInt(options.length);
			for (ItemStack stack : options)
				writeStack(stack, out);
		}
	}

	public static void writeStack(ItemStack stack, ByteArrayDataOutput out) {
		try {
			NBTBase.writeNamedTag(stack.writeToNBT(new NBTTagCompound()), out);
		}
		catch(IOException e) { } //Should never occur
	}

	public static ItemStack readStack(ByteArrayDataInput in) {
		try {
			return ItemStack.loadItemStackFromNBT((NBTTagCompound)NBTBase.readNamedTag(in));
		}
		catch(IOException e) { //Should never occur
			return null;
		} 
	}

}
