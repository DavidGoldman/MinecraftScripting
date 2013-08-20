package scripting.wrapper.entity;

import net.minecraft.entity.passive.EntitySheep;

public class ScriptSheep extends ScriptEntityAgeable {
	
	public final EntitySheep sheep;

	public ScriptSheep(EntitySheep sheep) {
		super(sheep);
		
		this.sheep = sheep;
	}
	
	public int getFleeceColor() {
		return sheep.getFleeceColor();
	}
	
	public void setFleeceColor(int color) {
		sheep.setFleeceColor(color);
	}
	
	public boolean getSheared() {
		return sheep.getSheared();
	}
	
	public void setSheared(boolean sheared) {
		sheep.setSheared(sheared);
	}

}
