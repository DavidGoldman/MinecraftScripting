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
	
	public final ScriptEntity selEntity;
	public final ScriptTileEntity selTile;
	private final AxisAlignedBB aabb;
	
	public final ScriptEntity[] selEntities;
	public final ScriptTileEntity[] selTiles;
	
	public ScriptSelection(Selection sel, EntityPlayer player) {
		selEntity = (sel.isEntitySelection()) ? ScriptEntity.createFromNative(sel.getSelectedEntity()) : null;
		selTile = (sel.isTileSelection()) ? new ScriptTileEntity(sel.getSelectedTile()) : null;
		if (sel.isRegionSelection()) {
			AxisAlignedBB pooledBB = sel.getAABB();
			aabb = AxisAlignedBB.getBoundingBox(pooledBB.minX, pooledBB.minY, pooledBB.minZ, pooledBB.maxX, pooledBB.maxY, pooledBB.maxZ);
			
			List<Entity> entList = sel.getEntitiesWithinAABB(player);
			selEntities = new ScriptEntity[entList.size()];
			for (int i = 0; i < selEntities.length; ++i)
				selEntities[i] = ScriptEntity.createFromNative(entList.get(i));
			
			List<TileEntity> tileList = sel.getTilesWithinAABB(player);
			selTiles = new ScriptTileEntity[tileList.size()];
			for (int i = 0; i < selTiles.length; ++i)
				selTiles[i] = new ScriptTileEntity(tileList.get(i));
		}
		else {
			aabb = null;
			selEntities = null;
			selTiles = null;
		}
	}
	
	public ScriptEntity[] getEntities() {
		return (isRegion()) ? selEntities : (isEntity()) ? new ScriptEntity[] { selEntity } : new ScriptEntity[0];
	}
	
	public ScriptTileEntity[] getTiles() {
		return (isRegion()) ? selTiles : (isTile()) ? new ScriptTileEntity[] { selTile } : new ScriptTileEntity[0];
	}
	
	public boolean isEntity() {
		return selEntity != null;
	}
	
	public boolean isTile() {
		return selTile != null;
	}
	
	public boolean isRegion() {
		return aabb != null;
	}
	
	public boolean isEmpty() {
		return selEntity == null && selTile == null && aabb == null;
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
