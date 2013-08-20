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
	
	public final ScriptEntity entity;
	public final ScriptTileEntity tile;
	private final AxisAlignedBB aabb;
	
	public final ScriptEntity[] entities;
	public final ScriptTileEntity[] tiles;
	
	public ScriptSelection(Selection sel, EntityPlayer player) {
		entity = (sel.isEntitySelection()) ? ScriptEntity.createFromNative(sel.getSelectedEntity()) : null;
		tile = (sel.isTileSelection()) ? new ScriptTileEntity(sel.getSelectedTile()) : null;
		if (sel.isRegionSelection()) {
			AxisAlignedBB pooledBB = sel.getAABB();
			aabb = AxisAlignedBB.getBoundingBox(pooledBB.minX, pooledBB.minY, pooledBB.minZ, pooledBB.maxX, pooledBB.maxY, pooledBB.maxZ);
			
			List<Entity> entList = sel.getEntitiesWithinAABB(player);
			entities = new ScriptEntity[entList.size()];
			for (int i = 0; i < entities.length; ++i)
				entities[i] = ScriptEntity.createFromNative(entList.get(i));
			
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
	
	public boolean isEntity() {
		return entity != null;
	}
	
	public boolean isTile() {
		return tile != null;
	}
	
	public boolean isRegion() {
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
}
