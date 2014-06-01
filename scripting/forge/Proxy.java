package scripting.forge;

import java.io.File;

import scripting.Selection;
import scripting.core.ScriptCore;
import scripting.network.ScriptPacketHandler;
import scripting.network.ServerPacketHandler;

public class Proxy {
	
	protected final ScriptPacketHandler server;
	
	public Proxy() {
		server = new ServerPacketHandler();
	}
	
	public File getMinecraftDir() {
		return new File(".");
	}
	
	public ScriptCore getClientCore() {
		return null;
	}
	
	public void setSelection(Selection s) { }
	public void postInit(Object... args) { }
	public void update() { }
	
	public ScriptPacketHandler getServerHandler() {
		return server;
	}

	//If you call getClientHandler() on the server, you're going to have a bad time.
	public ScriptPacketHandler getClientHandler() {
		return server;
	}
}
