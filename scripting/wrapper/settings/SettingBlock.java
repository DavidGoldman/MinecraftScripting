package scripting.wrapper.settings;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.block.Block;
import scripting.wrapper.world.ScriptBlock;

public class SettingBlock extends Setting {
	
	public int blockID;
	public int blockData;
	
	protected SettingBlock() { }

	/**
	 * 
	 * @param display Name of this setting
	 * @deprecated As of v1.1.0, block IDs are deprecated. Do NOT rely on them. Use {@link SettingBlock#SettingBlock(String, ScriptBlock)} instead.
	 */
	@Deprecated
	public SettingBlock(String display) {
		this(display, 0, 0);
	}
	
	/**
	 * 
	 * @param display Name of this setting
	 * @deprecated As of v1.1.0, block IDs are deprecated. Do NOT rely on them. Use {@link SettingBlock#SettingBlock(String, ScriptBlock)} instead.
	 */
	@Deprecated 
	public SettingBlock(String display, int blockID) {
		this(display, blockID, 0);
	}
	
	/**
	 * 
	 * @param display Name of this setting
	 * @deprecated As of v1.1.0, block IDs are deprecated. Do NOT rely on them. Use {@link SettingBlock#SettingBlock(String, ScriptBlock, int)} instead.
	 */
	@Deprecated 
	public SettingBlock(String display, int blockID, int blockData) {
		super(display);
		
		this.blockID = blockID;
		this.blockData = blockData;
	}
	
	public SettingBlock(String display, ScriptBlock block) {
		this(display, block, 0);
	}
	
	public SettingBlock(String display, ScriptBlock block, int blockData) {
		super(display);
		
		this.blockID = (block == null) ? 0 : Block.getIdFromBlock(block.block);
		this.blockData = blockData;
	}
	
	@Override
	public Object getValue() {
		return ScriptBlock.fromBlock(Block.getBlockById(blockID), blockData);
	}

	@Override
	protected void write(DataOutput out) throws IOException {
		super.write(out);
		out.writeInt(blockID);
		out.writeInt(blockData);
	}

	@Override
	protected Setting read(DataInput in) throws IOException {
		super.read(in);
		blockID = in.readInt();
		blockData = in.readInt();
		return this;
	}

}
