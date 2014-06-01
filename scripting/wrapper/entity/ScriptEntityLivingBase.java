package scripting.wrapper.entity;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import scripting.wrapper.ScriptItemStack;
import scripting.wrapper.ScriptVec3;

public class ScriptEntityLivingBase extends ScriptEntity {
	
	public final EntityLivingBase entityLivingBase;
	
	public ScriptEntityLivingBase(EntityLivingBase entityLivingBase) {
		super(entityLivingBase);
		
		this.entityLivingBase = entityLivingBase;
	}
	
	public void setHealth(float health) {
		entityLivingBase.setHealth(health);
	}
	
	public float getHealth() {
		return entityLivingBase.getHealth();
	}
	
	public boolean isChild() {
		return entityLivingBase.isChild();
	}
	
	public Random getRNG() {
		return entityLivingBase.getRNG();
	}
	
	public void clearActivePotions() {
		entityLivingBase.clearActivePotions();
	}
	
	public ScriptItemStack getHeldItem() {
		return ScriptItemStack.fromItemStack(entityLivingBase.getHeldItem());
	}
	
	public ScriptItemStack getCurrentItemOrArmor(int slot) {
		return ScriptItemStack.fromItemStack(entityLivingBase.getEquipmentInSlot(slot));
	}
	
	public void setCurrentItemOrArmor(int slot, ScriptItemStack is) {
		ItemStack stack = (is == null) ? null : is.stack;
		entityLivingBase.setCurrentItemOrArmor(slot, stack);
	}
	
	public void setSprinting(boolean sprint) {
		entityLivingBase.setSprinting(sprint);
	}
	
	public void setJumping(boolean jump) {
		entityLivingBase.setJumping(jump);
	}
	
	public void setPositionAndUpdate(ScriptVec3 vec) {
		entityLivingBase.setPositionAndUpdate(vec.x, vec.y, vec.z);
	}
	
	public boolean canEntityBeSeen(ScriptEntity entity) {
		return entityLivingBase.canEntityBeSeen(entity.entity);
	}

}
