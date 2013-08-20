package scripting.wrapper.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import scripting.wrapper.ScriptVec3;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptWorld;

public class ScriptEntity {
	
	public static ScriptEntity createFromNative(Entity entity) {
		if (entity instanceof EntityPlayer)
			return new ScriptPlayer((EntityPlayer)entity);
		if (entity instanceof EntityLivingBase)
			return new ScriptEntityLivingBase((EntityLivingBase)entity);
		return new ScriptEntity(entity);
	}

	public final Entity entity;

	public ScriptEntity(Entity entity) {
		this.entity = entity;
	}

	public int getEntityID() {
		return entity.entityId;
	}
	
	public String getInternalName() {
		return EntityList.getEntityString(entity);
	}

	public String getEntityName() {
		return entity.getEntityName();
	}
	
	public TAG_Compound writeToTag() {
		NBTTagCompound tag = new NBTTagCompound("ROOT");
		entity.writeToNBT(tag);
		return new TAG_Compound(tag);
	}
	
	public void readFromTag(TAG_Compound tag) {
		entity.readFromNBT(tag.tag);
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
