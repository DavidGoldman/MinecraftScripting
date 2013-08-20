package scripting.wrapper.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumGameType;

public class ScriptPlayer extends ScriptEntityLivingBase {
	
	public final EntityPlayer player;
	
	public ScriptPlayer(EntityPlayer player) {
		super(player);
		
		this.player = player;
	}
	
	public String getUsername() {
		return player.username;
	}
	
	public void addXPLevels(int levels) {
		player.addExperienceLevel(levels);
	}
	
	public void addXP(int xp) {
		player.addExperience(xp);
	}
	
	public int getXPLevel() {
		return player.experienceLevel;
	}
	
	public void setXPLevel(int level) {
		player.experienceLevel = level;
	}
	
	public void sendPlayerAbilities() {
		player.sendPlayerAbilities();
	}
	
	 public void addChatMessage(String str) {
		 player.addChatMessage(str);
	 }
	
	public void setGameType(int id) {
		player.setGameType(EnumGameType.getByID(id));
	}
	
	public void setDead() { }
	
	//Case in-sensitive 
	public void setGameType(String name) {
		player.setGameType(EnumGameType.getByName(name.toLowerCase()));
	}
}
