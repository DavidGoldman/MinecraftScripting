package scripting.wrapper.entity;

import net.minecraft.entity.passive.EntityPig;

public class ScriptPig extends ScriptEntityAgeable {
	
	public final EntityPig pig;

	public ScriptPig(EntityPig pig) {
		super(pig);
		
		this.pig = pig;
	}
	
	public void setSaddled(boolean saddle) {
		pig.setSaddled(saddle);
	}
	
	public boolean getSaddled() {
		return pig.getSaddled();
	}

}
