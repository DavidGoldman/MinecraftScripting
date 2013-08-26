package scripting.gui.settings;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import scripting.gui.settings.items.ItemPopup;
import scripting.wrapper.settings.SettingBlock;

public class SetBlockButton extends SetAbstractItemButton {
	
	private static List<ItemStack> blocks = null;

	private final SettingBlock setting;
	
	public SetBlockButton(SettingBlock setting) {
		super(setting.display, new ItemStack(setting.blockID, 1, setting.blockData), null);
		
		this.setting = setting;
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
			for (Item item : Item.itemsList)
				if (item instanceof ItemBlock)
					item.getSubItems(item.itemID, null, blocks);
			//CreativeTabs.tabBlock.displayAllReleventItems(blocks);
		}
		return blocks;
	}

}
