package scripting.gui.settings;

import net.minecraft.client.Minecraft;
import scripting.wrapper.settings.SettingString;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;
import com.mcf.davidee.guilib.vanilla.ButtonVanilla;

public class SetStringButton extends ButtonVanilla implements ISetting, Shiftable {
	
	private final SettingString setting;
	
	private int centerX;
	private int textX;

	public SetStringButton(SettingString setting) {
		super(Minecraft.getMinecraft().fontRenderer.getStringWidth(setting.selected) + 8, 15, setting.selected, null);
		
		this.setting = setting;
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.centerX = x;
		int stringWidth = mc.fontRenderer.getStringWidth(setting.display);
		textX = x - (width + stringWidth + 6)/2;
		super.setPosition(textX + stringWidth + 6, y);
	}
	
	public void setSelectedText(String text) {
		this.str = text;
		this.width = mc.fontRenderer.getStringWidth(str) + 8;
		setPosition(centerX, y);
	}
	
	@Override
	public void draw(int mx, int my) {
		mc.fontRenderer.drawString(setting.display, textX, y+3, 0xffffff);
		super.draw(mx, my);
	}
	

	@Override
	public void handleClick(int mx, int my) {
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		mc.displayGuiScreen(new PopupScreen(this, setting.options, (SettingsScreen)mc.currentScreen));
	}

	@Override
	public void applySetting() {
		setting.selected = str;
	}

	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}
	
	
	
}
