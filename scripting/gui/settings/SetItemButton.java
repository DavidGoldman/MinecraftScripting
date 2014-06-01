package scripting.gui.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scripting.gui.settings.items.ItemPopup;
import scripting.wrapper.settings.SettingItem;

public class SetItemButton extends SetAbstractItemButton {

	
	private static List<ItemStack> items = null;
	
	private final SettingItem setting;
	
	public SetItemButton(SettingItem setting) {
		super(setting.display, setting.selected, null);
		
		this.setting = setting;
	}
	
	@Override
	public void applySetting() {
		setting.selected = item;
	}
	
	@Override
	public void handleClick(int mx, int my) {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		mc.displayGuiScreen(new ItemPopup(this, getItems(), (SettingsScreen)mc.currentScreen));
	}
	
	private List<ItemStack> getItems() {
		if (setting.options != null && setting.options.length > 0) 
			return Arrays.asList(setting.options);
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (ItemStack item : mc.thePlayer.inventory.mainInventory) {
			if (item != null)
				list.add(item);
		}
		for (ItemStack armor : mc.thePlayer.inventory.armorInventory) {
			if (armor != null)
				list.add(armor);
		}
		list.addAll(getAllItems());
		return list;
	}
	
	/**
	 * See {@link net.minecraft.client.gui.inventory.GuiContainerCreative#updateCreativeSearch()}
	 */
	@SuppressWarnings("unchecked")
	private static List<ItemStack> getAllItems() {
		if (items == null){
			items = new ArrayList<ItemStack>();
			for (Iterator<Item> iterator = (Iterator<Item>)Item.itemRegistry.iterator(); iterator.hasNext();) {
				Item item = iterator.next();
				if (item != null && item.getCreativeTab() != null)
					item.getSubItems(item, null, items);
			}
			for (Enchantment ench : Enchantment.enchantmentsList)
				if (ench != null && ench.type != null)
					Items.enchanted_book.func_92113_a(ench, items);
		}
		return items;
	}

}
