package scripting.wrapper.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ScriptBlock {
	
	public final Block block;
	public int blockData;
	
	private ScriptBlock(Block b) {
		this(b, 0);
	}
	
	private ScriptBlock(Block block, int blockData) {
		this.block = block;
		this.blockData = blockData;
	}
	
	public String getBlockName() {
		return Block.blockRegistry.getNameForObject(block);
	}
	
	public boolean equalsIgnoreMetadata(ScriptBlock otherBlock) {
		return otherBlock != null && this.block == otherBlock.block;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof ScriptBlock)) 
			return false;
		ScriptBlock otherBlock = (ScriptBlock)obj;
		return otherBlock != null && this.block == otherBlock.block && this.blockData == otherBlock.blockData;
	}
	
	public static ScriptBlock atLocation(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		return (b == null) ? null : new ScriptBlock(b, world.getBlockMetadata(x, y, z));
	}
	
	public static ScriptBlock forName(String name) {
		return fromBlock(Block.getBlockFromName(name));
	}
	
	public static ScriptBlock fromBlock(Block b) {
		return (b == null) ? null : new ScriptBlock(b);
	}
	
	public static ScriptBlock fromBlock(Block b, int data) {
		return (b == null) ? null : new ScriptBlock(b, data);
	}
}
