package scripting.wrapper.entity;

import net.minecraft.entity.passive.EntityWolf;

public class ScriptWolf extends ScriptEntityTameable {

	public final EntityWolf wolf;
	
	public ScriptWolf(EntityWolf wolf) {
		super(wolf);
		
		this.wolf = wolf;
	}
	
	public boolean isAngry() {
		return wolf.isAngry();
	}
	
	public void setAngry(boolean angry) {
		wolf.setAngry(angry);
	}
	
	public int getCollarColor() {
		return wolf.getCollarColor();
	}
	
	public void setCollarColor(int color) {
		wolf.setCollarColor(color);
	}
	
}
