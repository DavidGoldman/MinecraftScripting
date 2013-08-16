package scripting.forge;

import java.io.File;

import scripting.Selection;
import scripting.core.ScriptCore;

public class Proxy {
	
	public File getMinecraftDir() {
		return new File(".");
	}
	
	public void setSelection(Selection s) { }
	
	public void postInit(Object... args) { }
	
	public void update() { }
	
	public ScriptCore getClientCore() {
		return null;
	}
}
