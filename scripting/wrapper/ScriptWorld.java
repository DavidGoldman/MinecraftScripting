package scripting.wrapper;

import scripting.wrapper.entity.ScriptEntity;
import net.minecraft.world.World;

public class ScriptWorld {
	
	private final World world;
	
	public ScriptWorld(World world) {
		this.world = world;
	}
	
	public int getBlockID(int x, int y, int z) {
		return world.getBlockId(x, y, z);
	}
	
	public void setBlockID(int x, int y, int z, int blockID) {
		world.setBlock(x, y, z, blockID);
	}
	
	public int getBlockMetadata(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public void setBlockMetadata(int x, int y, int z, int metadata) {
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}
	
	public void setBlockMetadataWithNotify(int x, int y, int z, int metadata, int flag) {
		world.setBlockMetadataWithNotify(x, y, z, metadata, flag);
	}
	
	public void setBlockAndMetadata(int x, int y, int z, int blockID, int metadata) {
		world.setBlock(x, y, z, blockID, metadata, 2);
	}
	
	public void setBlockAndMetaDataWithNotify(int x, int y, int z, int blockID, int metadata, int flag) {
		world.setBlock(x, y, z, blockID, metadata, flag);
	}
	
	public float getLightBrightness(int x, int y, int z) {
		return world.getLightBrightness(x, y, z);
	}
	
	public int getBlockLightValue(int x, int y, int z) {
		return world.getBlockLightValue(x, y, z);
	}
	
	public void spawnEntityInWorld(ScriptEntity scriptEnt) {
		world.spawnEntityInWorld(scriptEnt.entity);
	}
}
