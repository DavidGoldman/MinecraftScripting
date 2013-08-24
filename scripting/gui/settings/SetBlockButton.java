package scripting.gui.settings;

import net.minecraft.item.ItemStack;
import scripting.gui.settings.items.ItemButton;
import scripting.wrapper.settings.SettingBlock;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;

public class SetBlockButton extends ItemButton implements ISetting, Shiftable {

	private final SettingBlock setting;
	private int textX;
	
	public SetBlockButton(SettingBlock setting) {
		super(new ItemStack(setting.blockID, 1, setting.blockData), null);
		this.setting = setting;
	}
	
	@Override
	public void draw(int mx, int my) {
		mc.fontRenderer.drawString(setting.display, textX, y+3, 0xffffff);
		super.draw(mx, my);
	}
	
	@Override
	public void setPosition(int x, int y) {
		//X is the center
		int stringWidth = mc.fontRenderer.getStringWidth(setting.display);
		textX = x - (width + stringWidth + 6)/2;
		super.setPosition(textX + stringWidth + 6, y);
	}
	
	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}

	@Override
	public void applySetting() {
		setting.blockID = item.itemID;
		setting.blockData = item.getItemDamage();
	}

}
