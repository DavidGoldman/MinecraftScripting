package scripting.wrapper.entity;

import scripting.wrapper.ScriptVec3;
import scripting.wrapper.ScriptWorld;
import net.minecraft.entity.Entity;

public class ScriptEntity {
	
	public final Entity entity;
	
	public ScriptEntity(Entity entity) {
		this.entity = entity;
	}
	
	public int getEntityID() {
		return entity.entityId;
	}
	
	public ScriptVec3 getPosition() {
		return new ScriptVec3(entity.posX, entity.posY, entity.posZ);
	}
	
	public ScriptWorld getWorld() {
		return new ScriptWorld(entity.worldObj);
	}
}
