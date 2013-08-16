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
	
	public String getEntityName() {
		return entity.getEntityName();
	}
	
	public String getTranslatedEntityName() {
		return entity.getTranslatedEntityName();
	}
	
	public ScriptVec3 getPosition() {
		return new ScriptVec3(entity.posX, entity.posY, entity.posZ);
	}
	
	public void setPosition(ScriptVec3 vec) {
		entity.setPosition(vec.x, vec.y, vec.z);
	}
	
	public ScriptVec3 getVelocity() {
		return new ScriptVec3(entity.motionX, entity.motionY, entity.motionZ);
	}
	
	public void setVelocity(ScriptVec3 vec) {
		entity.setVelocity(vec.x, vec.y, vec.z);
	}
	
	public ScriptDataWatcher getDataWatcher() {
		return new ScriptDataWatcher(entity.getDataWatcher());
	}
	
	public ScriptWorld getWorld() {
		return new ScriptWorld(entity.worldObj);
	}
	
	public float getFallDistance() {
		return entity.fallDistance;
	}
	
	public void setFallDistance(float dist) {
		entity.fallDistance = dist;
	}
	
	public boolean isAlive() {
		return entity.isEntityAlive();
	}
	
	public boolean isDead() {
		return entity.isDead;
	}
	
	public double getDistanceToEntity(ScriptEntity se) {
		return entity.getDistanceToEntity(se.entity);
	}
	
	public double getDistanceSqToEntity(ScriptEntity se) {
		return entity.getDistanceSqToEntity(se.entity);
	}
}
