package scripting;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import scripting.utils.BlockCoord;
import scripting.utils.Utils;

public class Selection {

	private int dimension;
	private BlockCoord corner1, corner2;
	private Entity selectedEntity;
	private TileEntity selectedTile;

	//Creates an empty selection
	public Selection(int dimension) {
		this.dimension = dimension;
	}

	public Selection(int dimension, Entity selectedEntity) {
		this.dimension = dimension;
		this.selectedEntity = selectedEntity;
	}
	
	public Selection(int dimension, TileEntity selectedTile) {
		this.dimension = dimension;
		this.selectedTile = selectedTile;
	}

	public Selection(int dimension, BlockCoord corner1, BlockCoord corner2) {
		this.dimension = dimension;
		this.corner1 = corner1;
		this.corner2 = corner2;
	}

	public void setSelectedEntity(Entity e) {
		selectedEntity = e;
		selectedTile = null;
		corner1 = null;
		corner2 = null;
	}
	
	public void setTileEntity(TileEntity t) {
		selectedTile = t;
		selectedEntity = null;
		corner1 = null;
		corner2 = null;
	}

	/**
	 * Reset selected entity/tile if applicable, else - 
	 * If c is already a corner in this selection, it is removed.
	 * Otherwise, if the selection has an open spot, it is added.
	 * In the case 2 corners are already selected, the selection is reset.
	 * @param c BlockCoord to add
	 */
	public void addBlockCoord(BlockCoord c) {
		if (selectedEntity != null || selectedTile != null) {
			selectedEntity = null;
			selectedTile = null;
			return;
		}
		
		if (c.equals(corner1)) { //Remove corner1
			corner1 = corner2;
			corner2 = null;
		}
		else if (c.equals(corner2))  //Remove corner2
			corner2 = null;
		else if (corner1 == null)
			corner1 = c;
		else if (corner2 == null)
			corner2 = c;
		else 
			corner1 = corner2 = null;
	}

	public boolean isEntitySelection() {
		return selectedEntity != null;
	}
	
	public boolean isTileSelection() {
		return selectedTile != null;
	}
	
	public boolean isRegionSelection() {
		return corner1 != null;
	}

	public boolean isEmpty() {
		return corner1 == null && selectedEntity == null && selectedTile == null;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void reset(int dimension) {
		this.dimension = dimension;
		selectedEntity = null;
		selectedTile = null;
		corner1 = corner2 = null;
	}

	public AxisAlignedBB getAABB() {
		if (corner2 == null)
			return AxisAlignedBB.getAABBPool().getAABB(corner1.x, corner1.y, corner1.z, corner1.x + 1, corner1.y + 1, corner1.z + 1);
		int minX = getMin(corner1.x, corner2.x);
		int minY = getMin(corner1.y, corner2.y);
		int minZ = getMin(corner1.z, corner2.z);
		int maxX = getMax(corner1.x+1, corner2.x+1);
		int maxY = getMax(corner1.y+1, corner2.y+1);
		int maxZ = getMax(corner1.z+1, corner2.z+1);
		return AxisAlignedBB.getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public List<Entity> getEntitiesWithinAABB(EntityPlayer player) {
		return (List<Entity>) player.worldObj.getEntitiesWithinAABBExcludingEntity(player, getAABB(), Utils.NO_PLAYERS);
	}
	
	public List<TileEntity> getTilesWithinAABB(EntityPlayer player) {
		return Utils.getTilesInSelectionAABB(player.worldObj, getAABB());
	}

	public Entity getSelectedEntity() {
		return selectedEntity;
	}
	
	public TileEntity getSelectedTile() {
		return selectedTile;
	}

	public BlockCoord getCorner1() {
		return corner1;
	}

	public BlockCoord getCorner2() {
		return corner2;
	}

	private int getMin(int var1, int var2) {
		return (var1 < var2) ? var1 : var2;
	}

	private int getMax(int var1, int var2) {
		return (var1 > var2) ? var1 : var2;
	}

	public boolean isInvalid() {
		return (selectedTile != null && selectedTile.isInvalid()) || (selectedEntity != null && selectedEntity.isDead);
	}
}
