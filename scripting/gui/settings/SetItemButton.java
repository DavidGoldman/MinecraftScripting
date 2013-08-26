package scripting.gui.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
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
	private static List<ItemStack> getAllItems() {
		if (items == null){
			items = new ArrayList<ItemStack>();
			for (Item item : Item.itemsList)
				if (item != null && item.getCreativeTab() != null)
					item.getSubItems(item.itemID, null, items);
			for (Enchantment ench : Enchantment.enchantmentsList)
				if (ench != null && ench.type != null)
					Item.enchantedBook.func_92113_a(ench, items);
		}
		return items;
	}

}
