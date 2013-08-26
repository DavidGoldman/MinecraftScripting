package scripting.gui.settings.items;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import scripting.gui.ScriptOverlay;
import scripting.gui.settings.SetAbstractItemButton;
import scripting.gui.settings.SettingsScreen;

import com.mcf.davidee.guilib.basic.BasicScreen;
import com.mcf.davidee.guilib.core.Button;
import com.mcf.davidee.guilib.core.Button.ButtonHandler;
import com.mcf.davidee.guilib.core.Container;
import com.mcf.davidee.guilib.core.Scrollbar;
import com.mcf.davidee.guilib.core.Widget;
import com.mcf.davidee.guilib.vanilla.ScrollbarVanilla;

public class ItemPopup extends ScriptOverlay implements ButtonHandler {

	private final int SCROLLBAR_WIDTH = 10;
	private final List<ItemStack> options;
	private final SetAbstractItemButton itemButton;

	private Scrollbar scrollbar;
	private Container container;
	private int x, y;

	public ItemPopup(SetAbstractItemButton itemButton, List<ItemStack> options, BasicScreen bg) {
		super(bg);

		this.itemButton = itemButton;
		this.options = options;
	}

	@Override
	protected void createGui() {
		scrollbar = new ScrollbarVanilla(SCROLLBAR_WIDTH);
		container = new Container(scrollbar, 0, 0);

		Widget[] widgets = new Widget[options.size()];
		for (int i = 0; i < widgets.length; ++i)
			widgets[i] = new ItemButton(options.get(i), this);

		container.addWidgets(widgets);
		containers.add(container);
		this.selectedContainer = container;
	}

	@Override
	protected void revalidateGui() {
		super.revalidateGui();
		List<Widget> widgets = container.getWidgets();

		int xButtons = width/ItemButton.WIDTH - 6;
		int yButtons = height/ItemButton.HEIGHT - 5;

		int lines = widgets.size() / xButtons;
		if (widgets.size() % xButtons != 0)
			++lines;
		if (lines > 8)
			lines = 8;
		yButtons = MathHelper.clamp_int(yButtons, 1, lines);

		int cWidth = xButtons*(ItemButton.WIDTH) + SCROLLBAR_WIDTH;
		int cHeight = yButtons*(ItemButton.HEIGHT);
		int startX = (width - cWidth)/2;
		int startY = SettingsScreen.getStartY(height) + SettingsScreen.CONTAINER_HEIGHT/2 - cHeight/2;

		int lineCount = 0;
		int x = startX;
		int y = startY;
		for (Widget w : widgets) {
			w.setPosition(x, y);
			++lineCount;
			x += ItemButton.HEIGHT;
			if (lineCount + 1 > xButtons) {
				lineCount = 0;
				x = startX;
				y += ItemButton.HEIGHT;
			}
		}

		scrollbar.setPosition(startX + cWidth - SCROLLBAR_WIDTH, startY);
		container.revalidate(startX, startY, cWidth, cHeight);
	}

	@Override
	public void drawBackground() {
		super.drawBackground();

		//Draw the 4 outlining rectangles (entire top, entire bottom, left (mid), right (mid) )
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		drawRect(container.left()-1, container.top()-1, container.right() - SCROLLBAR_WIDTH + 1, container.top(), 0xffffffff);
		drawRect(container.left()-1, container.bottom(), container.right() - SCROLLBAR_WIDTH + 1, container.bottom()+1, 0xffffffff);
		drawRect(container.left()-1, container.top(), container.left(), container.bottom(), 0xffffffff);
		drawRect(container.right() - SCROLLBAR_WIDTH, container.top(), container.right() - SCROLLBAR_WIDTH + 1, container.bottom(), 0xffffffff);
		drawRect(container.left(), container.top(), container.right() - SCROLLBAR_WIDTH, container.bottom(), 0xff444444);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	/**
	 * "Hacky" close if user clicked outside of this container
	 */
	@Override
	protected void mouseClicked(int mx, int my, int code) {
		if (code == 0) {
			if (container.inBounds(mx, my))
				container.mouseClicked(mx, my);
			else
				close();
		}
	}

	@Override
	public void buttonClicked(Button button) {
		itemButton.setItem(((ItemButton)button).item);
		close();
	}



}
