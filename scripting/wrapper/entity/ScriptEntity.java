package scripting.wrapper.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import scripting.wrapper.ScriptVec3;
import scripting.wrapper.nbt.TAG_Compound;
import scripting.wrapper.world.ScriptWorld;

public class ScriptEntity {
	
	public static ScriptEntity createEntityByName(String name, ScriptWorld world) {
		Entity e = EntityList.createEntityByName(name, world.world);
		return (e != null) ? createFromNative(e) : null;
	}
	
	public static ScriptEntity createFromNative(Entity entity) {
		if (entity instanceof EntityPlayer)
			return new ScriptPlayer((EntityPlayer)entity);
		
		if (entity instanceof EntityOcelot)
			return new ScriptOcelot((EntityOcelot)entity);
		if (entity instanceof EntityWolf)
			return new ScriptWolf((EntityWolf)entity);
		if (entity instanceof EntityTameable)
			return new ScriptEntityTameable((EntityTameable)entity);
		
		if (entity instanceof EntityPig)
			return new ScriptPig((EntityPig)entity);
		if (entity instanceof EntitySheep)
			return new ScriptSheep((EntitySheep)entity);
		if (entity instanceof EntityVillager)
			return new ScriptVillager((EntityVillager)entity);
		if (entity instanceof EntityAgeable)
			return new ScriptEntityAgeable((EntityAgeable)entity);
		
		if (entity instanceof EntityLivingBase)
			return new ScriptEntityLivingBase((EntityLivingBase)entity);
		return new ScriptEntity(entity);
	}
	
	@SuppressWarnings("unchecked")
	public static String[] getAllEntityNames() {
		return ((Set<String>)EntityList.stringToClassMapping.keySet()).toArray(new String[0]);
	}
	
	
	@SuppressWarnings("unchecked")
	public static String[] getAllLivingEntityNames() {
		List<String> names = new ArrayList<String>();
		for (Entry<String, Class<?>> entry : ((Map<String, Class<?>>)EntityList.stringToClassMapping).entrySet()) {
			Class<?> clazz = entry.getValue();
			if (!Modifier.isAbstract(clazz.getModifiers()) && EntityLiving.class.isAssignableFrom(clazz)) 
				names.add(entry.getKey());
		}
		return names.toArray(new String[0]);
	}
	
	public final Entity entity;

	public ScriptEntity(Entity entity) {
		this.entity = entity;
	}

	public int getEntityID() {
		return entity.getEntityId();
	}
	
	public String getInternalName() {
		return EntityList.getEntityString(entity);
	}

	public String getEntityName() {
		return entity.getCommandSenderName();
	}
	
	public TAG_Compound writeToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		entity.writeToNBT(tag);
		return new TAG_Compound(tag);
	}
	
	public void readFromTag(TAG_Compound tag) {
		entity.readFromNBT(tag.tag);
	}

	public String getTranslatedEntityName() {
		return getEntityName();
	}

	public ScriptVec3 getPosition() {
		return new ScriptVec3(entity.posX, entity.posY, entity.posZ);
	}
	
	public double getX() {
		return entity.posX;
	}
	public double getY() {
		return entity.posY;
	}
	public double getZ() {
		return entity.posZ;
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
	
	public void mountEntity(ScriptEntity scriptEnt) {
		entity.mountEntity((scriptEnt == null) ? null : scriptEnt.entity);
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
	
	public void setDead() {
		entity.worldObj.removeEntity(entity);
	}

	public double getDistanceToEntity(ScriptEntity se) {
		return entity.getDistanceToEntity(se.entity);
	}

	public double getDistanceSqToEntity(ScriptEntity se) {
		return entity.getDistanceSqToEntity(se.entity);
	}
	
	public String toString() {
		return "ScriptEntity: " + entity.toString();
	}
}
