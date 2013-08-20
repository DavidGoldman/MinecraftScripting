package scripting.gui.settings;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import scripting.gui.ScriptScreen;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.SettingsPacket;
import scripting.wrapper.settings.Setting;
import scripting.wrapper.settings.SettingBoolean;
import scripting.wrapper.settings.SettingFloat;
import scripting.wrapper.settings.SettingInt;
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
		title.setPosition(width/2, height/4 - 30);
		save.setPosition(width/2 - 70, height/4 + 140);
		close.setPosition(width/2 + 20, height/4 + 140);
		mainC.revalidate(0, 0, width, height);
		
		int y = height/4  - 13;
		for (Widget w : setC.getWidgets()) {
			w.setPosition(width/2, y);
			y += w.getHeight() + 5;
		}
		
		scrollbar.setPosition(width/2 + 125, height/4 - 15);
		setC.revalidate(width/2 - 125, height/4 - 15, 260, 145);
	}

	@Override
	protected void createGui() {
		mainC = new Container();
		title = new Label("Filter: " + pkt.script.replace(".filter", ""));
		close = new ButtonVanilla(50, 20, "Cancel", new CloseHandler());
		save = new ButtonVanilla(50, 20, "Filter", this);
		mainC.addWidgets(title, save, close);
		
		scrollbar = new ScrollbarVanilla(10);
		setC = new Container(scrollbar, 10, 4);
		Widget[] widgets = new Widget[pkt.settings.length];
		for (int i = 0; i < widgets.length; ++i) 
			widgets[i] = getSettingWidget(pkt.settings[i]);
		setC.addWidgets(widgets);
		
		containers.add(setC);
		containers.add(mainC);
		selectedContainer = setC;
	}
	
	private Widget getSettingWidget(Setting s) {
		if (s instanceof SettingBoolean)
			return new SetCheckbox((SettingBoolean)s);
		if (s instanceof SettingInt)
			return new SetIntTextField((SettingInt)s);
		if (s instanceof SettingFloat)
			return new SetFloatTextField((SettingFloat)s);
		return new SetStringButton((SettingString)s);
	}
	
	@Override
	public void buttonClicked(Button button) {
		for (Widget w : setC.getWidgets())
			((ISetting)w).applySetting();
		PacketDispatcher.sendPacketToServer(ScriptPacket.getPacket(PacketType.SETTINGS, pkt.script, pkt.settings));
	}
	
	@Override
	public void drawBackground() {
		super.drawBackground();
		drawRect(setC.left(), setC.top(), setC.right() - 10, setC.bottom(), 0x44444444);
	}

}
