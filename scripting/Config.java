package scripting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;

//TODO Script timeout option?
public class Config {
	private static boolean opOnly = true;
	private static String[] whitelist = new String[0];
	
	public static boolean filtersPauseGame = false;
	public static boolean defaultFilters = true;
	public static boolean forceNewFilters = true;
	
	private static Configuration config;
	
	public static void load(Configuration config) {
		Config.config = config;
		config.load();
		
		defaultFilters = config.get("General", "DefaultFilters", defaultFilters, "Generate default filters?").getBoolean(defaultFilters);
		filtersPauseGame = config.get("General", "FiltersPauseGame", filtersPauseGame, "Do filters pause the game?").getBoolean(filtersPauseGame);
		forceNewFilters = config.get("General", "ForceNewFilters", forceNewFilters, "Force new filters?").getBoolean(forceNewFilters);
		
		opOnly = config.get("General", "OpOnly", opOnly, "True: Only OPs can use scripts; False: Ops + Whitelisted users").getBoolean(opOnly);
		whitelist = config.get("General", "Whitelist", whitelist).getStringList();
		config.save();
	}
	
	public static void save() {
		if (config != null) {
			defaultFilters = config.get("General", "DefaultFilters", defaultFilters, "Generate default filters?").getBoolean(defaultFilters);
			filtersPauseGame = config.get("General", "FiltersPauseGame", filtersPauseGame, "Do filters pause the game?").getBoolean(filtersPauseGame);
			config.get("General", "ForceNewFilters", forceNewFilters, "Force new filters?").set(forceNewFilters);
			
			opOnly = config.get("General", "OpOnly", opOnly, "True: Only OPs can use scripts; False: Ops + Whitelisted users").getBoolean(opOnly);
			whitelist = config.get("General", "Whitelist", whitelist).getStringList();
			config.save();
		}
	}
	
	
	
	public static boolean hasPermission(EntityPlayer player) {
		String username = player.getCommandSenderName();
		return MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(username) || (!opOnly && whitelisted(username));
	}
	
	private static boolean whitelisted(String username) {
		for (String s : whitelist)
			if (s.equalsIgnoreCase(username))
				return true;
		return false;
	}
}
