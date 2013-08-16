package scripting.wrapper.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TAG_List extends TAG_Base {

	public final NBTTagList list;

	public TAG_List() {
		super(new NBTTagList());

		list = (NBTTagList)base;
	}

	public TAG_List(String name) {
		super(new NBTTagList(name));

		list = (NBTTagList)base;
	}

	public TAG_List(NBTTagList list) {
		super(list);

		this.list = list;
	}

	public TAG_Base copy() {
		return new TAG_List((NBTTagList)list.copy());
	}

	public void appendTag(TAG_Base tag) {
		list.appendTag(tag.base);
	}

	public TAG_Base removeTag(int index) {
		return TAG_Base.createFromNative(list.removeTag(index));
	}

	public TAG_Base tagAt(int index) {
		return TAG_Base.createFromNative(list.tagAt(index));
	}

	public int tagCount() {
		return list.tagCount();
	}


}
