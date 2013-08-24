package scripting.gui.settings.items;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

	public final ItemStack item;
	private final List<Widget> tooltip;

	private boolean hover;

	public ItemButton(ItemStack item, ButtonHandler handler) {
		super(WIDTH, HEIGHT, handler);

		this.item = item;
		this.tooltip = Arrays.asList((Widget)new ItemTooltip(item, mc.currentScreen));
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
		}  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.func_110434_K() /*TextureManager*/, item, x, y);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public List<Widget> getTooltips() {
		return (hover) ? tooltip : super.getTooltips();
	}
}
