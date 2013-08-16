package scripting.gui;

import scripting.ScriptingMod;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;

import com.mcf.davidee.guilib.basic.Label;
import com.mcf.davidee.guilib.basic.Tooltip;
import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.vanilla.ButtonVanilla;

import cpw.mods.fml.common.network.PacketDispatcher;

public class MainScreen extends ScriptScreen {
	
	public static final int REFRESH_TICKS = 200; //Check every 10 seconds
	
	private Container container;
	private Label title;
	private Button close, client, server;
	
	private boolean serverScripts;
	
	public MainScreen() {
		super(null);
	}
	
	public void setServerScripts(boolean flag) {
		server.setEnabled(flag);
		serverScripts = flag;
	}

	@Override
	protected void revalidateGui() {
		title.setPosition(width/2, height/4 - 20);
		client.setPosition(width/2 - 50, height/4 + 18);
		server.setPosition(width/2 - 50, height/4 + 43);
		close.setPosition(width/2 - 75, height/4 + 90);
		container.revalidate(0, 0, width, height);
	}

	@Override
	protected void createGui() {
		container = new Container();
		title = new Label("Scripting Mod", new Tooltip("v" + ScriptingMod.VERSION));
		client = new ButtonVanilla(100, 20, "Client", this);
		client.setEnabled(ScriptingMod.proxy.getClientCore().hasScripts());
		server = new ButtonVanilla(100, 20, "Server", this);
		server.setEnabled(false);
		close = new ButtonVanilla(150, 20, "Close", new CloseHandler());
		
		container.addWidgets(title, close, client, server);
		containers.add(container);
		selectedContainer = container;
		
		PacketDispatcher.sendPacketToServer(ScriptPacket.getPacket(PacketType.HAS_SCRIPTS));
	}
	
	public void buttonClicked(Button button) { 
		if (button == client)
			mc.displayGuiScreen(new ClientMenu(this));
		if (button == server)
			PacketDispatcher.sendPacketToServer(ScriptPacket.getRequestPacket(PacketType.STATE));
	}
	
	@Override
	public void reopenedGui() {
		PacketDispatcher.sendPacketToServer(ScriptPacket.getPacket(PacketType.HAS_SCRIPTS));
	}

}
