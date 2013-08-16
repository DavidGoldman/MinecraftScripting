package scripting;

import net.minecraftforge.common.Configuration;

public class Config {
	
	public static int selectorID = 4987;
	
	public static void load(Configuration config) {
		config.load();
		config.getItem("selectorID", selectorID);
		config.save();
	}
}
