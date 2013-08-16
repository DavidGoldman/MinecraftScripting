package scripting.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SItem extends Item {
	
	public SItem(int id) {
		super(id);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegist) {
		itemIcon = iconRegist.registerIcon("scripting:" + getUnlocalizedName().substring(5));
	}
}
