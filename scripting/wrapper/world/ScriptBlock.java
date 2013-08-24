package scripting.wrapper.world;

public class ScriptBlock {
	
	public int blockID;
	public int blockData;
	
	public ScriptBlock() {
		this(0, 0);
	}
	
	public ScriptBlock(int blockID, int blockData) {
		this.blockID = blockID;
		this.blockData = blockData;
	}
}
