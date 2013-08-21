package scripting.wrapper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import org.mozilla.javascript.ContinuationPending;

import scripting.ReflectionHelper;
import scripting.ScriptingMod;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.wrapper.entity.ScriptEntity;
import scripting.wrapper.tileentity.ScriptTileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ScriptHelper {

	public static void sleep(int ticks) throws ContinuationPending, IllegalAccessException, IllegalArgumentException {
		ScriptingMod.instance.scriptSleep(ticks);
	}

	public static void syncEntityNBT(ScriptEntity entity) throws IllegalAccessException {
		if (ScriptingMod.instance.isClient())
			throw new IllegalAccessException("Can only sync NBT from server");
		NBTTagCompound tag = new NBTTagCompound();
		entity.entity.writeToNBT(tag);
		int dim = entity.entity.dimension;
		PacketDispatcher.sendPacketToAllInDimension(ScriptPacket.getPacket(PacketType.ENTITY_NBT,
				 entity.entity.entityId, tag), dim);
		if (entity.entity instanceof EntityLivingBase) 
			ReflectionHelper.potionsNeedUpdate.setBoolean(entity.entity, true);
	}

	public static void syncTileEntityNBT(ScriptTileEntity te) throws IllegalAccessException {
		if (ScriptingMod.instance.isClient())
			throw new IllegalAccessException("Can only sync NBT from server");
		TileEntity tile = te.tile;
		NBTTagCompound tag = new NBTTagCompound();
		tile.writeToNBT(tag);
		int dim = tile.worldObj.provider.dimensionId;
		PacketDispatcher.sendPacketToAllInDimension(ScriptPacket.getPacket(PacketType.TILE_NBT, 
				tile.xCoord, tile.yCoord, tile.zCoord, tag), dim);
	}

}
