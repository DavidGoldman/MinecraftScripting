package scripting.wrapper.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;

public class ScriptEntityTameable extends ScriptEntityAgeable {
	
	public final EntityTameable entityTameable;

	public ScriptEntityTameable(EntityTameable entityTameable) {
		super(entityTameable);

		this.entityTameable = entityTameable;
	}
	
	public boolean getTamed() {
		return entityTameable.isTamed();
	}
	
	public void setTamed(boolean tamed) {
		entityTameable.setTamed(tamed);
	}
	
	public boolean getSitting() {
		return entityTameable.isSitting();
	}
	
	public void setSitting(boolean sitting) {
		entityTameable.setSitting(sitting);
	}
	
	public String getOwnerName() {
		return entityTameable.getOwnerName();
	}
	
	public void setOwnerName(String name) {
		entityTameable.setOwner(name);
	}

}
