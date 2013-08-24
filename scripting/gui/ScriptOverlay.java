package scripting.gui;

import org.lwjgl.input.Keyboard;

import com.mcf.davidee.guilib.basic.BasicScreen;
import com.mcf.davidee.guilib.basic.OverlayScreen;

public abstract class ScriptOverlay extends OverlayScreen {

	public ScriptOverlay(BasicScreen bg) {
		super(bg);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return getParent().doesGuiPauseGame();
	}
	
	@Override
	protected void unhandledKeyTyped(char c, int code) {
		if (code == Keyboard.KEY_ESCAPE || c == '\r')
			close();
	}
}
