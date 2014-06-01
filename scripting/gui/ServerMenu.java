package scripting.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import scripting.ScriptingMod;
import scripting.core.ScriptCore.State;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.packet.StatePacket;

import com.mcf.davidee.guilib.basic.FocusedContainer;
import com.mcf.davidee.guilib.basic.Label;
import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.core.Scrollbar;
import com.mcf.davidee.guilib.focusable.FocusableLabel;
import com.mcf.davidee.guilib.focusable.FocusableWidget;
import com.mcf.davidee.guilib.vanilla.ButtonVanilla;
import com.mcf.davidee.guilib.vanilla.ScrollbarVanilla;

/*
 * Slightly modified copy of ClientMenu
 */
public class ServerMenu extends ScriptScreen {
	
	public static final int REFRESH_TICKS = 100;
	
	private final StatePacket pkt;
	
	private Container mainC, labelC;
	private Scrollbar scrollbar;
	private Label title;
	private Button close, toggle;
	
	private int counter;

	public ServerMenu(StatePacket pkt, GuiScreen parent) {
		super(parent);
		
		this.pkt = pkt;
	}
	
	public void updateScreen() {
		super.updateScreen();
		setToggleText();
		if (++counter == REFRESH_TICKS) {
			ScriptingMod.DISPATCHER.sendToServer(ScriptPacket.getRequestPacket(PacketType.STATE));
			counter = 0;
		}
	}
	
	private void setToggleText() {
		FocusableWidget fw = labelC.getFocusedWidget();
		if (fw != null) {
			boolean running = (Boolean) ((FocusableLabel)fw).getUserData();
			toggle.setText((running) ? "Stop" : "Start");
		}
	}

	@Override
	protected void revalidateGui() {
		title.setPosition(width/2, height/4 - 20);
		toggle.setPosition(width/2 - 25, height/4 + 113);
		close.setPosition(width/2 - 50, height/4 + 135);
		mainC.revalidate(0, 0, width, height);

		scrollbar.setPosition(width/2 + 70,height/4 - 5);
		List<FocusableWidget> groups = labelC.getFocusableWidgets();
		for (int i = 0; i < groups.size(); ++i)
			groups.get(i).setPosition(width/2, height/4 - 3 + i*14);

		labelC.revalidate(width/2 - 70, height/4 - 5, 150, 115);
	}

	@Override
	protected void createGui() {
		mainC = new Container();
		title = new Label("Server Scripts");
		close = new ButtonVanilla(100, 20, "Back", new CloseHandler());
		toggle = new ButtonVanilla(50, 20, "Toggle", this);
		mainC.addWidgets(title, toggle, close);
		
		scrollbar = new ScrollbarVanilla(10);
		labelC = new FocusedContainer(scrollbar, 14, 4);
		
		FocusableWidget[] widgets = new FocusableWidget[pkt.states.length];
		
		for (int i = 0; i < widgets.length; ++i) {
			State state = pkt.states[i];
			if (state.running)
				widgets[i] = new FocusableLabel(getName(state.script), 0xff6600, 0xffcc00, 0xff9966);
			else
				widgets[i] = new FocusableLabel(getName(state.script), 0x66ff, 0xccff, 0x6699ff);
			((FocusableLabel)widgets[i]).setUserData(state.running);
		}
		labelC.addWidgets(widgets);
		toggle.setEnabled(widgets.length > 0);
		setToggleText();

		containers.add(labelC);
		containers.add(mainC);
		selectedContainer = labelC;
	} 
	
	public void buttonClicked(Button b) {
		FocusableLabel label = (FocusableLabel) labelC.getFocusedWidget();
		String script = ((FocusableLabel)labelC.getFocusedWidget()).getText() + ".js";
		b.setEnabled(false);
		State[] state  = new State[] { new State(script, (Boolean) label.getUserData())} ;
		ScriptingMod.DISPATCHER.sendToServer(ScriptPacket.getPacket(PacketType.STATE, (Object)state));
	}
	
	//Remove the ".js" ending
	private String getName(String name) {
		return name.substring(0, name.length() - 3);
	}
	
	@Override
	public void drawBackground() {
		super.drawBackground();
		drawRect(labelC.left(), labelC.top(), labelC.right() - 10, labelC.bottom(), 0x44444444);
	}

	public void update(StatePacket newPkt) {
		counter = 0;
		if (newPkt.states.length == pkt.states.length && newPkt.states.length == labelC.getFocusableWidgets().size()) { //Nothing wrong
			List<FocusableWidget> widgets = labelC.getFocusableWidgets();
			for (int i = 0; i < widgets.size(); ++i) {
				FocusableLabel label = (FocusableLabel)widgets.get(i);
				State state = newPkt.states[i];
				if (state.running)
					label.setColors(0xff6600, 0xffcc00, 0xff9966);
				else
					label.setColors(0x66ff, 0xccff, 0x6699ff);
				label.setUserData(state.running);
			}
			toggle.setEnabled(true);
		}
	}

}
