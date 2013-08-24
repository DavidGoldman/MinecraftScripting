package scripting.gui.settings.items;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Widget;

public class ItemButton extends Button {

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final RenderItem itemRenderer = new RenderItem();

	protected ItemStack item;
	protected List<Widget> tooltip;

	private GuiScreen parent;
	protected boolean hover;

	public ItemButton(ItemStack item, ButtonHandler handler) {
		super(WIDTH, HEIGHT, handler);

		this.parent = mc.currentScreen;
		setItem(item);
	}

	protected void setItem(ItemStack item) {
		if (item.getItem() == null && item.itemID != 0)
			throw new IllegalArgumentException("Item to display does not exist");
		this.item = item;
		this.tooltip = Arrays.asList((Widget)new ItemTooltip(item, parent));
	}

	/**
	 * Draws the item. Note sure if this is 100% correct at the moment.
	 */
	@Override
	public void draw(int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		hover = inBounds(mx, my);
		if (hover) {
			drawRect(x, y, x + width, y + height, 0x55909090);
			tooltip.get(0).setPosition(mx, my);
		}
		drawRect(x-1, y-1, x+width+1, y, 0xff000000);
		drawRect(x-1, y+height, x+width+1, y+height+1, 0xff000000);
		drawRect(x-1, y, x, y+height, 0xff000000);
		drawRect(x+width, y, x+width+1, y+height, 0xff000000);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (item.itemID != 0) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.func_110434_K() /*TextureManager*/, item, x, y);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else  //Air
			drawString(mc.fontRenderer, "Air" , x + 2, y + 4, -1);

	}

	@Override
	public List<Widget> getTooltips() {
		return (hover) ? tooltip : super.getTooltips();
	}
}
