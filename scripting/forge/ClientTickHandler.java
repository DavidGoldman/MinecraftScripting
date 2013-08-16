package scripting.forge;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import scripting.ScriptingMod;
import scripting.core.ClientCore;
import scripting.core.ScriptCore;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements IScheduledTickHandler {

	private static final int SELECTION_UPDATE = 5;

	private int counter;
	private ClientCore clientCore;

	public ClientTickHandler(File scriptDir, Map<String, Object> props, Map<String, Class<?>> abbreviations) {
		this.clientCore = new ClientCore(new File(scriptDir, "client"), props, abbreviations);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) { }

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		clientCore.tick();

		if (++counter == SELECTION_UPDATE) {
			ScriptingMod.proxy.update();
			counter = 0;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "scripting.client";
	}

	@Override
	public int nextTickSpacing() {
		return 1;
	}

	public ScriptCore getClientCore() {
		return clientCore;
	}

}
