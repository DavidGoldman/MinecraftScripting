package scripting.gui.settings;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;
import com.mcf.davidee.guilib.vanilla.items.ItemButton;

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
		this.zLevel = (item.getItem() != null && item.hasEffect(0)) ? 50 : 0;
	}
	
	@Override
	public void draw(int mx, int my) {
		mc.fontRenderer.drawString(display, textX, y+3, 0xffffff);
		super.draw(mx, my);
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		drawRect(x, y, x + width, y + 1, 0xff000000);
		drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
		drawRect(x, y, x + 1, y + height, 0xff000000);
		drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
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
