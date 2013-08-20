package scripting.wrapper.entity;

import net.minecraft.entity.passive.EntityOcelot;

public class ScriptOcelot extends ScriptEntityTameable {
	
	public final EntityOcelot ocelot;
	
	public ScriptOcelot(EntityOcelot ocelot) {
		super(ocelot);
		
		this.ocelot = ocelot;
	}
	
	public int getTameSkin() {
		return ocelot.getTameSkin();
	}
	
	public void setTameSkin(int skin) {
		ocelot.setTameSkin(skin);
	}
}
