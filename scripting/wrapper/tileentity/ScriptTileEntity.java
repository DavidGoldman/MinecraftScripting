package scripting.wrapper.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import scripting.ReflectionHelper;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptBlock;
import scripting.wrapper.world.ScriptWorld;


public class ScriptTileEntity {
	
	public static ScriptTileEntity createFromTag(TAG_Compound tag) {
		TileEntity te = TileEntity.createAndLoadEntity(tag.tag);
		return (te != null) ? new ScriptTileEntity(te) : null;
	}
	
	public static String[] getAllTileNames() {
		return ReflectionHelper.tileClassToString.values().toArray(new String[0]);
	}
	
	public final TileEntity tile;

	public ScriptTileEntity(TileEntity tile) {
		this.tile = tile;
	}

	public ScriptWorld getWorld() {
		return new ScriptWorld(tile.getWorldObj());
	}
	
	public String getInternalName() {
		return getInternalName(tile);
	}

	public TAG_Compound writeToTag() {
		NBTTagCompound tag = new NBTTagCompound();
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
		tile.markDirty();
	}

	public ScriptBlock getBlock() {
		return ScriptBlock.atLocation(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
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
