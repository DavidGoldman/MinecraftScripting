package scripting.wrapper.entity;

import net.minecraft.entity.EntityAgeable;

public class ScriptEntityAgeable extends ScriptEntityLivingBase {
	
	public final EntityAgeable entityAgeable;

	public ScriptEntityAgeable(EntityAgeable entityAgeable) {
		super(entityAgeable);
		
		this.entityAgeable = entityAgeable;
	}
	
	public ScriptEntityAgeable createChild(ScriptEntityAgeable entity) {
		return new ScriptEntityAgeable(entityAgeable.createChild(entity.entityAgeable));
	}
	
	public int getGrowingAge() {
		return entityAgeable.getGrowingAge();
	}
	
	public void setGrowingAge(int age) {
		entityAgeable.setGrowingAge(age);
	}

}
