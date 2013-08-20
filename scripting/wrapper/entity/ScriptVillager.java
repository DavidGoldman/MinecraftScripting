package scripting.wrapper.entity;

import net.minecraft.entity.passive.EntityVillager;

public class ScriptVillager extends ScriptEntityAgeable {

	public final EntityVillager villager;

	public ScriptVillager(EntityVillager villager) {
		super(villager);

		this.villager = villager;
	}

	public void setProfession(int prof) {
		villager.setProfession(prof);
	}

	public int getProfession() {
		return villager.getProfession();
	}

	public void setMating(boolean mating) {
		villager.setMating(mating);
	}

	public boolean getMating() {
		return villager.isMating();
	}

	public void setPlaying(boolean playing) {
		villager.setPlaying(playing);
	}

	public boolean getPlaying() {
		return villager.isPlaying();
	}
}
