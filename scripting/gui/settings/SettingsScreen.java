package scripting.gui.settings;

import net.minecraft.client.gui.GuiScreen;
import scripting.Config;
import scripting.gui.ScriptScreen;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SettingsPacket;
import scripting.wrapper.settings.Setting;
import scripting.wrapper.settings.SettingBlock;
import scripting.wrapper.settings.SettingBoolean;
import scripting.wrapper.settings.SettingFloat;
import scripting.wrapper.settings.SettingInt;
import scripting.wrapper.settings.SettingItem;
import scripting.wrapper.settings.SettingList;
import scripting.wrapper.settings.SettingString;

import com.mcf.davidee.guilib.basic.Label;
import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.core.Scrollbar;
import com.mcf.davidee.guilib.core.Widget;
import com.mcf.davidee.guilib.vanilla.ButtonVanilla;
import com.mcf.davidee.guilib.vanilla.ScrollbarVanilla;

import cpw.mods.fml.common.network.PacketDispatcher;

public class SettingsScreen extends ScriptScreen {
	
	public static final int SCROLLBAR_WIDTH = 10;
	public static final int CONTAINER_WIDTH = 260;
	public static final int CONTAINER_HEIGHT = 145;
	
	public static int getStartY(int height) {
		return height/4 - 15;
	}
	
	private final SettingsPacket pkt;
	
	private Scrollbar scrollbar;
	private Container mainC, setC;
	private Label title;
	private Button save, close;
	

	public SettingsScreen(SettingsPacket pkt, GuiScreen parent) {
		super(parent);
		
		this.pkt = pkt;
	}

	@Override
	protected void revalidateGui() {
		int startY = getStartY(height);
		title.setPosition(width/2, startY - 15);
		save.setPosition(width/2 - 70, startY + 155);
		close.setPosition(width/2 + 20, startY + 155);
		mainC.revalidate(0, 0, width, height);
		
		int y = startY + 2;
		for (Widget w : setC.getWidgets()) {
			w.setPosition(width/2, y);
			y += w.getHeight() + 5;
		}
		
		final int realWidth = CONTAINER_WIDTH - SCROLLBAR_WIDTH;
		scrollbar.setPosition(width/2 + realWidth/2, startY);
		setC.revalidate(width/2 - realWidth/2, startY, CONTAINER_WIDTH, CONTAINER_HEIGHT);
	}

	@Override
	protected void createGui() {
		mainC = new Container();
		title = new Label("Filter: " + pkt.script.replace(".filter", ""));
		close = new ButtonVanilla(50, 20, "Cancel", new CloseHandler());
		save = new ButtonVanilla(50, 20, "Filter", this);
		mainC.addWidgets(title, save, close);
		
		scrollbar = new ScrollbarVanilla(SCROLLBAR_WIDTH);
		setC = new Container(scrollbar, 10, 4);
		Widget[] widgets = new Widget[pkt.settings.length];
		for (int i = 0; i < widgets.length; ++i) 
			widgets[i] = getSettingWidget(pkt.settings[i]);
		setC.addWidgets(widgets);
		
		containers.add(setC);
		containers.add(mainC);
		selectedContainer = setC;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return Config.filtersPauseGame;
	}
	
	private Widget getSettingWidget(Setting s) {
		if (s instanceof SettingBoolean)
			return new SetCheckbox((SettingBoolean)s);
		if (s instanceof SettingInt)
			return new SetIntTextField((SettingInt)s);
		if (s instanceof SettingFloat)
			return new SetFloatTextField((SettingFloat)s);
		if (s instanceof SettingString)
			return new SetStringTextField((SettingString)s);
		if (s instanceof SettingBlock)
			return new SetBlockButton((SettingBlock)s);
		if (s instanceof SettingItem)
			return new SetItemButton((SettingItem)s);
		return new SetListButton((SettingList)s);
	}
	
	@Override
	public void buttonClicked(Button button) {
		for (Widget w : setC.getWidgets())
			((ISetting)w).applySetting();
		PacketDispatcher.sendPacketToServer(ScriptPacket.getPacket(PacketType.SETTINGS, pkt.script, pkt.settings));
	}
	
	@Override
	public void drawBackground() {
		drawRect(0, 0, width, height, 0x80101010);
		drawRect(setC.left(), setC.top(), setC.right() - 10, setC.bottom(), 0x55555555);
	}

}
