package scripting.wrapper.settings;

import scripting.wrapper.world.ScriptBlock;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class SettingBlock extends Setting {
	
	public int blockID;
	public int blockData;

	protected SettingBlock(ByteArrayDataInput in) {
		super(in);
		
		this.blockID = in.readInt();
		this.blockData = in.readInt();
	}
	
	public SettingBlock(String display) {
		this(display, 0, 0);
	}
	
	public SettingBlock(String display, int blockID) {
		this(display, blockID, 0);
	}
	
	public SettingBlock(String display, int blockID, int blockData) {
		super(display);
		
		this.blockID = blockID;
		this.blockData = blockData;
	}
	
	@Override
	public Object getValue() {
		return new ScriptBlock(blockID, blockData);
	}

	@Override
	protected void write(ByteArrayDataOutput out) {
		out.writeInt(blockID);
		out.writeInt(blockData);
	}

}
