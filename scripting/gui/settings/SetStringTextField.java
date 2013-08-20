package scripting.gui.settings;

import scripting.utils.Utils;
import scripting.wrapper.settings.SettingString;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;
import com.mcf.davidee.guilib.vanilla.TextFieldVanilla;

public class SetStringTextField extends TextFieldVanilla implements ISetting, Shiftable {
	
	private final SettingString setting;
	private int textX;

	public SetStringTextField(SettingString setting) {
		super(60, 14, new VanillaFilter());

		setMaxLength(30);
		setText(setting.str);
		this.setting = setting;
	}
	
	@Override
	protected void drawBackground() {
		mc.fontRenderer.drawString(setting.display, textX, y+3, 0xffffff);
		super.drawBackground();
	}
	
	@Override
	public void setPosition(int x, int y) {
		//X is the center
		int stringWidth = mc.fontRenderer.getStringWidth(setting.display);
		textX = x - (width + stringWidth + 6)/2;
		super.setPosition(textX + stringWidth + 6, y);
	}

	@Override
	public void applySetting() {
		setting.str = text;
	}

	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}
}
