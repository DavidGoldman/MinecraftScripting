package scripting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

public class Config {
	
	private static boolean opOnly = true;
	private static String[] whitelist = new String[0];
	
	public static boolean defaultFilters = true;
	public static int selectorID = 4987;
	
	public static void load(Configuration config) {
		config.load();
		config.getItem("SelectorID", selectorID);
		opOnly = config.get("General", "OpOnly", opOnly, "True: Only OPs can use scripts; False: Ops + Whitelisted users").getBoolean(opOnly);
		defaultFilters = config.get("General", "DefaultFilters", defaultFilters, "Generate default filters?").getBoolean(defaultFilters);
		whitelist = config.get("General", "Whitelist", whitelist).getStringList();
		config.save();
	}
	
	public static boolean hasPermission(EntityPlayer player) {
		return MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(player.username) || (!opOnly && whitelisted(player.username));
	}
	
	private static boolean whitelisted(String username) {
		for (String s : whitelist)
			if (s.equalsIgnoreCase(username))
				return true;
		return false;
	}
}
