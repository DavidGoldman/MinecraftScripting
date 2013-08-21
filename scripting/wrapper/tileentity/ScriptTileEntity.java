package scripting.wrapper.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import scripting.ReflectionHelper;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptWorld;


public class ScriptTileEntity {
	
	public static ScriptTileEntity createFromTag(TAG_Compound tag) {
		TileEntity te = TileEntity.createAndLoadEntity(tag.tag);
		return (te != null) ? new ScriptTileEntity(te) : null;
	}
	
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
		return (String) ReflectionHelper.tileClassToString.get(te.getClass());
	}
}
