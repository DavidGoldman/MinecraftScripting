package scripting.utils;

import net.minecraft.util.AxisAlignedBB;

public final class BlockCoord {
	
	public int x, y, z;
	
	public BlockCoord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (! (obj instanceof BlockCoord))
			return false;
		BlockCoord coord = (BlockCoord)obj;
		return this.x == coord.x && this.y == coord.y && this.z == coord.z;
	}
	
	public AxisAlignedBB getAABB() {
		return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
	}
	
	public String toString() {
		return "{" + x + ',' + y + ',' + z + '}';
	}
}