package scripting.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import scripting.ScriptingMod;
import scripting.Selection;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.utils.BlockCoord;

public class SelectorItem extends SItem {

	public SelectorItem(int id) {
		super(id);

		bFull3D = true;
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("selector");
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
		if (player instanceof EntityPlayerMP) {
			Selection sel = ScriptingMod.instance.getSelection((EntityPlayerMP)player); 
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null)
				sel.setTileEntity(te);
			else
				sel.addBlockCoord(new BlockCoord(x, y, z));
			((EntityPlayerMP)player).playerNetServerHandler.sendPacketToPlayer(ScriptPacket.getPacket(PacketType.SELECTION, sel));
		}
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP) {
			Selection sel = ScriptingMod.instance.getSelection((EntityPlayerMP)player); 
			sel.setSelectedEntity(entity);
			((EntityPlayerMP)player).playerNetServerHandler.sendPacketToPlayer(ScriptPacket.getPacket(PacketType.SELECTION, sel));
		}
		return true;
	}

}
