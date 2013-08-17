package scripting.wrapper;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import scripting.Selection;
import scripting.wrapper.entity.ScriptEntity;
import scripting.wrapper.tileentity.ScriptTileEntity;

public class ScriptSelection {
	
	public final ScriptEntity selectedEntity;
	public final ScriptTileEntity selectedTile;
	private final AxisAlignedBB aabb;
	
	public final ScriptEntity[] entities;
	public final ScriptTileEntity[] tiles;
	
	public ScriptSelection(Selection sel, EntityPlayer player) {
		selectedEntity = (sel.isEntitySelection()) ? new ScriptEntity(sel.getSelectedEntity()) : null;
		selectedTile = (sel.isTileSelection()) ? new ScriptTileEntity(sel.getSelectedTile()) : null;
		if (sel.isRegionSelection()) {
			AxisAlignedBB pooledBB = sel.getAABB();
			aabb = AxisAlignedBB.getBoundingBox(pooledBB.minX, pooledBB.minY, pooledBB.minZ, pooledBB.maxX, pooledBB.maxY, pooledBB.maxZ);
			
			List<Entity> entList = sel.getEntitiesWithinAABB(player);
			entities = new ScriptEntity[entList.size()];
			for (int i = 0; i < entities.length; ++i)
				entities[i] = new ScriptEntity(entList.get(i));
			
			List<TileEntity> tileList = sel.getTilesWithinAABB(player);
			tiles = new ScriptTileEntity[tileList.size()];
			for (int i = 0; i < tiles.length; ++i)
				tiles[i] = new ScriptTileEntity(tileList.get(i));
		}
		else {
			aabb = null;
			entities = null;
			tiles = null;
		}
	}
	
	public boolean isEntitySelection() {
		return selectedEntity != null;
	}
	
	public boolean isTileSelection() {
		return selectedTile != null;
	}
	
	public boolean isRegionSelection() {
		return aabb != null;
	}
	
	public int getMinX() {
		return (int) aabb.minX;
	}
	public int getMaxX() {
		return (int) aabb.maxX - 1;
	}
	
	public int getMinY() {
		return (int) aabb.minY;
	}
	public int getMaxY() {
		return (int) aabb.maxY - 1;
	}
	
	public int getMinZ() {
		return (int) aabb.minZ;
	}
	public int getMaxZ() {
		return (int) aabb.maxZ - 1;
	}
	
	/*
	 * This is a really bad way to deal with ranges.
	 * TODO Implement a JS Range object similar to the one found here:
	 * https://gist.github.com/tschaub/3291399
	 * 
	 * Array ranges can be iterated with "for each (var in arr)", but true
	 * JS iterators can be iterated with "for (var in arr)"
	 */
	public int[] getXRange() {
		int min = getMinX(), max = getMaxX();
		int[] arr = new int[max - min + 1];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = min+i;
		return arr;
	}
	public int[] getYRange() {
		int min = getMinY(), max = getMaxY();
		int[] arr = new int[max - min + 1];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = min+i;
		return arr;
	}
	public int[] getZRange() {
		int min = getMinZ(), max = getMaxZ();
		int[] arr = new int[max - min + 1];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = min+i;
		return arr;
	}
}
