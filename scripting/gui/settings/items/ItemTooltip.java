package scripting.gui.settings.items;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import scripting.ScriptingMod;

import com.mcf.davidee.guilib.core.Widget;

public class ItemTooltip extends Widget {

	private final List<String> tooltips;
	private final FontRenderer font;
	private final GuiScreen parent;

	/**
	 * See {@link net.minecraft.client.gui.inventory.GuiContainer#drawScreen} for more information.
	 * @param stack
	 */
	public ItemTooltip(ItemStack stack, GuiScreen parent) {
		super(0, 0);

		tooltips = stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
		if (!tooltips.isEmpty()) {
			tooltips.set(0, ScriptingMod.SECTION + Integer.toHexString(stack.getRarity().rarityColor) + tooltips.get(0));
			for (int i = 1; i < tooltips.size(); ++i)
				tooltips.set(i, EnumChatFormatting.GRAY + tooltips.get(i));
		}
		FontRenderer itemRenderer = stack.getItem().getFontRenderer(stack);
		font = (itemRenderer == null) ? mc.fontRenderer : itemRenderer;
		this.parent = parent;
		this.width = getMaxStringWidth();
		this.height = (tooltips.size() > 1) ? tooltips.size()*10 : 8; 
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x + 12;
		this.y = y - 12;
		if (x + width > parent.width)
			x -= 28 + width;
		if (y + height + 6 > parent.height)
			y = parent.height - height - 6;
	}


	/**
	 * See {@link net.minecraft.client.gui.inventory.GuiContainer#drawHoveringText}
	 */
	@Override
	public void draw(int mx, int my) { 
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		if (!tooltips.isEmpty()) {
			final int outlineColor = 0xf0100010;
			drawRect(x - 3, y - 4, x + width + 3, y - 3, outlineColor);
			drawRect(x - 3, y + height + 3, x + width + 3, y + height + 4, outlineColor);
			drawRect(x - 3, y - 3, x + width + 3, y + height + 3, outlineColor);
			drawRect(x - 4, y - 3, x - 3, y + height + 3, outlineColor);
			drawRect(x + width + 3, y - 3, x + width + 4, y + height + 3, outlineColor);
			int gradient1 = 1347420415;
			int gradient2 = (gradient1 & 16711422) >> 1 | gradient1 & -16777216;
			drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, gradient1, gradient2);
			drawGradientRect(x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, gradient1, gradient2);
			drawGradientRect(x - 3, y - 3, x + width + 3, y - 3 + 1, gradient1, gradient1);
			drawGradientRect(x - 3, y + height + 2, x + width + 3, y + height + 3, gradient2, gradient2);
			for (int index = 0; index < tooltips.size(); ++index) {
				font.drawStringWithShadow(tooltips.get(index), x, y, -1);
				if (index == 0)
					y += 2;
				y += 10;
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public boolean click(int mx, int my) {
		return false;
	}

	private int getMaxStringWidth() {
		int max = 0;
		for (String s : tooltips) {
			int width = font.getStringWidth(s);
			if (width > max)
				max = width;
		}
		return max;
	}

}