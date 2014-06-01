package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import scripting.packet.ScriptPacket;
import scripting.wrapper.ScriptItemStack;

public class SettingItem extends Setting {

	public ItemStack selected;
	public ItemStack[] options;
	private boolean writeOptions;

	protected SettingItem() { }

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
		return ScriptItemStack.fromItemStack(selected);
	}
	
	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		ScriptPacket.writeItemStack(selected, out);
		out.writeBoolean(writeOptions);
		if (writeOptions) {
			out.write(options.length);
			for (ItemStack stack : options)
				ScriptPacket.writeItemStack(stack, out);
		}
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		selected = ScriptPacket.readItemStack(in);
		if (in.readBoolean()) { //has options
			options = new ItemStack[in.readInt()];
			for (int i = 0; i < options.length; ++i)
				options[i] = ScriptPacket.readItemStack(in);
		}
		writeOptions = false;
		return this;
	}

}
