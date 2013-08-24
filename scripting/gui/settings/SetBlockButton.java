package scripting.gui.settings;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import scripting.gui.settings.items.ItemButton;
import scripting.gui.settings.items.ItemPopup;
import scripting.wrapper.settings.SettingBlock;

import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;

public class SetBlockButton extends ItemButton implements ISetting, Shiftable {
	
	private static List<ItemStack> blocks = null;

	private final SettingBlock setting;
	private int textX;
	
	public SetBlockButton(SettingBlock setting) {
		super(new ItemStack(setting.blockID, 1, setting.blockData), null);
		
		this.setting = setting;
		this.zLevel = 0;
	}
	
	@Override
	public void draw(int mx, int my) {
		mc.fontRenderer.drawString(setting.display, textX, y+3, 0xffffff);
		super.draw(mx, my);
		drawRect(x, y, x + width, y + 1, 0xff000000);
		drawRect(x, y + height - 1, x + width, y + height, 0xff000000);
		drawRect(x, y, x + 1, y + height, 0xff000000);
		drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
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
	
	@Override
	public void handleClick(int mx, int my) {
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		mc.displayGuiScreen(new ItemPopup(this, getBlocks(), (SettingsScreen)mc.currentScreen));
	}
	
	private static List<ItemStack> getBlocks() {
		if (blocks == null){
			blocks = new ArrayList<ItemStack>();
			blocks.add(new ItemStack(0, 0, 0)); //Add air
			CreativeTabs.tabBlock.displayAllReleventItems(blocks);
		}
		return blocks;
	}

}
