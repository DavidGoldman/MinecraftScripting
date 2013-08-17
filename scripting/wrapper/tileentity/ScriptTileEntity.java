package scripting.wrapper.tileentity;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptWorld;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ScriptTileEntity {

	private static final Map CLASS_TO_STRING = ObfuscationReflectionHelper.getPrivateValue(TileEntity.class, null, 1);
	
	public final TileEntity tile;

	public ScriptTileEntity(TileEntity tile) {
		this.tile = tile;
	}

	public ScriptWorld getWorld() {
		return new ScriptWorld(tile.worldObj);
	}
	
	public String getInternalName() {
		return getInternalName(tile);
	}

	public TAG_Compound writeToTag() {
		NBTTagCompound tag = new NBTTagCompound("ROOT");
		tile.writeToNBT(tag);
		return new TAG_Compound(tag);
	}

	public void readFromTag(TAG_Compound tag) {
		tile.readFromNBT(tag.tag);
	}

	public int getBlockMetadata() {
		return tile.getBlockMetadata();
	}

	public void onInventoryChanged() {
		tile.onInventoryChanged();
	}

	public int getBlockID() {
		return tile.getBlockType().blockID;
	}

	public int getX() {
		return tile.xCoord;
	}

	public int getY() {
		return tile.yCoord;
	}

	public int getZ() {
		return tile.zCoord;
	}

	private static String getInternalName(TileEntity te) {
		return (String) CLASS_TO_STRING.get(te.getClass());
	}
}
