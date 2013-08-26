package scripting.gui.settings;

import net.minecraft.item.ItemStack;
import scripting.gui.settings.items.ItemButton;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;

public abstract class SetAbstractItemButton extends ItemButton implements ISetting, Shiftable {

	protected final String display;
	protected int textX;
	
	public SetAbstractItemButton(String display, ItemStack item, ButtonHandler handler) {
		super(item, handler);
		
		this.display = display;
	}
	
	@Override
	public void setItem(ItemStack item) {
		super.setItem(item);
		this.zLevel = (item.hasEffect(0)) ? 50 : 0;
	}
	
	@Override
	public void draw(int mx, int my) {
		mc.fontRenderer.drawString(display, textX, y+3, 0xffffff);
		super.draw(mx, my);
		drawRect(x, y, x + width, y + 1, 0xff000000);
		drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
		drawRect(x, y, x + 1, y + height, 0xff000000);
		drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
	}
	
	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}
	
	@Override
	public void setPosition(int x, int y) {
		//X is the center
		int stringWidth = mc.fontRenderer.getStringWidth(display);
		textX = x - (width + stringWidth + 6)/2;
		super.setPosition(textX + stringWidth + 6, y);
	}

}
