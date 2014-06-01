package scripting.gui.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scripting.gui.settings.items.ItemPopup;
import scripting.wrapper.settings.SettingBlock;

public class SetBlockButton extends SetAbstractItemButton {
	
	private static List<ItemStack> blocks = null;

	private final SettingBlock setting;
	
	public SetBlockButton(SettingBlock setting) {
		super(setting.display, new ItemStack(Block.getBlockById(setting.blockID), 1, setting.blockData), null);
		
		this.setting = setting;
	}

	
	@Override
	public void applySetting() {
		setting.blockID = Item.getIdFromItem(item.getItem()); //TODO Verify continuing validity - assert Item.getIDFromItem(null) == 0
		setting.blockData = (item.getItem() != null) ? item.getItemDamage() : 0;
	}
	
	@Override
	public void handleClick(int mx, int my) {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		mc.displayGuiScreen(new ItemPopup(this, getBlocks(), (SettingsScreen)mc.currentScreen));
	}
	
	@SuppressWarnings("unchecked")
	private static List<ItemStack> getBlocks() {
		if (blocks == null){
			blocks = new ArrayList<ItemStack>();
			blocks.add(new ItemStack(Block.getBlockFromName("air"), 0, 0)); //same as null, 0, 0
			for (Iterator<Item> iterator = (Iterator<Item>)Item.itemRegistry.iterator(); iterator.hasNext();) {
				Item item = iterator.next();
				if (item instanceof ItemBlock)
					item.getSubItems(item, null, blocks);
			}
			//CreativeTabs.tabBlock.displayAllReleventItems(blocks);
		}
		return blocks;
	}

}
