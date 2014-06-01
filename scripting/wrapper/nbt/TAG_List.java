package scripting.wrapper.nbt;

import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class TAG_List extends TAG_Base {

	public final NBTTagList list;

	public TAG_List() {
		super(new NBTTagList());

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

	//TODO Improve/cache field?
	public TAG_Base tagAt(int index) {
		List<NBTBase> tagList = ReflectionHelper.getPrivateValue(NBTTagList.class, list, 0);
		return TAG_Base.createFromNative(tagList.get(index));
	}

	public int tagCount() {
		return list.tagCount();
	}

	public static TAG_List newFloatList(float... list) {
        NBTTagList tagList = new NBTTagList();
        for (float f : list)
        	tagList.appendTag(new NBTTagFloat(f));
        return new TAG_List(tagList);
    }

	public static TAG_List newDoubleList(double... list) {
        NBTTagList tagList = new NBTTagList();
        for (double d : list)
        	tagList.appendTag(new NBTTagDouble(d));
        return new TAG_List(tagList);
    }


}
