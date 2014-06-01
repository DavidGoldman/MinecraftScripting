package scripting.gui.settings;

import scripting.utils.Utils;
import scripting.wrapper.settings.SettingFloat;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;
import com.mcf.davidee.guilib.vanilla.TextFieldVanilla;

public class SetFloatTextField extends TextFieldVanilla implements ISetting, Shiftable {
	
	private final SettingFloat setting;
	private int textX;

	public SetFloatTextField(SettingFloat setting) {
		super(60, 14, new FloatFilter());

		setMaxLength(15);
		setText("" + setting.val);
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
		setting.val = Utils.parseFloatWithDMinMax(text, setting.val, setting.min, setting.max);
	}

	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}
	
	public static class FloatFilter implements CharacterFilter {
		public String filter(String s) {
			StringBuilder sb = new StringBuilder();
			for (char c : s.toCharArray())
				if (isAllowedCharacter(c))
					sb.append(c);
			return sb.toString();
		}

		public boolean isAllowedCharacter(char c) {
			return Character.isDigit(c) || c == '.' || c == '-';
		}
	}
}
