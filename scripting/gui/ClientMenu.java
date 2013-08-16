package scripting.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import scripting.ScriptingMod;
import scripting.core.ScriptCore.State;
import scripting.core.script.JSScript;

import com.mcf.davidee.guilib.basic.FocusedContainer;
import com.mcf.davidee.guilib.basic.Label;
import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.core.Scrollbar;
import com.mcf.davidee.guilib.focusable.FocusableLabel;
import com.mcf.davidee.guilib.focusable.FocusableWidget;
import com.mcf.davidee.guilib.vanilla.ButtonVanilla;
import com.mcf.davidee.guilib.vanilla.ScrollbarVanilla;

public class ClientMenu extends ScriptScreen {

	private Container mainC, labelC;
	private Scrollbar scrollbar;
	private Label title;
	private Button close, toggle;

	public ClientMenu(GuiScreen parent) {
		super(parent);
	}
	
	public void updateScreen() {
		super.updateScreen();
		setToggleText();
	}
	
	private void setToggleText() {
		FocusableWidget fw = labelC.getFocusedWidget();
		if (fw != null) {
			toggle.setEnabled(true);
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
		title = new Label("Client Scripts");
		close = new ButtonVanilla(100, 20, "Back", new CloseHandler());
		toggle = new ButtonVanilla(50, 20, "Toggle", this);
		toggle.setEnabled(false);
		mainC.addWidgets(title, toggle, close);
		
		scrollbar = new ScrollbarVanilla(10);
		labelC = new FocusedContainer(scrollbar, 14, 4);
		
		List<State> list = ScriptingMod.proxy.getClientCore().getBasicScripts();
		FocusableWidget[] widgets = new FocusableWidget[list.size()];
		for (int i = 0; i < widgets.length; ++i) {
			State state = list.get(i);
			if (state.running)
				widgets[i] = new FocusableLabel(getName(state.script), 0xff6600, 0xffcc00, 0xff9966);
			else
				widgets[i] = new FocusableLabel(getName(state.script), 0x66ff, 0xccff, 0x6699ff);
			((FocusableLabel)widgets[i]).setUserData(state.running);
		}
		labelC.addWidgets(widgets);
		setToggleText();

		containers.add(labelC);
		containers.add(mainC);
		selectedContainer = labelC;
	} 
	
	//Remove the ".js" ending
	private String getName(String name) {
		return name.substring(0, name.length() - 3);
	}
	
	public void buttonClicked(Button b) {
		FocusableLabel label = (FocusableLabel) labelC.getFocusedWidget();
		String script = ((FocusableLabel)labelC.getFocusedWidget()).getText() + ".js";
		ScriptingMod.proxy.getClientCore().toggleScript(script);
		boolean running = ScriptingMod.proxy.getClientCore().isScriptRunning(script);
		label.setUserData(running);
		if (running)
			label.setColors(0xff6600, 0xffcc00, 0xff9966);
		else
			label.setColors(0x66ff, 0xccff, 0x6699ff);
	}
	

	@Override
	public void drawBackground() {
		super.drawBackground();
		drawRect(labelC.left(), labelC.top(), labelC.right() - 10, labelC.bottom(), 0x44444444);
	}

}
