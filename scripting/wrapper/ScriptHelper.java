package scripting.wrapper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import org.mozilla.javascript.ContinuationPending;

import scripting.ReflectionHelper;
import scripting.ScriptingMod;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import scripting.wrapper.entity.ScriptEntity;
import scripting.wrapper.tileentity.ScriptTileEntity;

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
		ScriptingMod.DISPATCHER.sendToDimension(ScriptPacket.getPacket(PacketType.ENTITY_NBT,
				 entity.entity.getEntityId(), tag), dim);
		if (entity.entity instanceof EntityLivingBase) 
			ReflectionHelper.potionsNeedUpdate.setBoolean(entity.entity, true);
	}

	
	/**
	 * Sync the tile entity using NBT tags
	 * 
	 * @deprecated As of mod version 1.0.1, replaced by {@link #syncTileEntity(ScriptTileEntity)}, which uses vanilla methods to sync tiles.
	 * @param te ScripTileEntity to sync
	 * @throws IllegalAccessException When called from the client
	 */
	@Deprecated
	public static void syncTileEntityNBT(ScriptTileEntity te) throws IllegalAccessException {
		if (ScriptingMod.instance.isClient())
			throw new IllegalAccessException("Can only sync NBT from server");
		TileEntity tile = te.tile;
		NBTTagCompound tag = new NBTTagCompound();
		tile.writeToNBT(tag);
		int dim = tile.getWorldObj().provider.dimensionId;
		ScriptingMod.DISPATCHER.sendToDimension(ScriptPacket.getPacket(PacketType.TILE_NBT, 
				tile.xCoord, tile.yCoord, tile.zCoord, tag), dim);
	}

	/**
	 * Syncs the tile entity using the vanilla method (preferred).
	 * 
	 * @param te ScripTileEntity to sync
	 * @throws IllegalAccessException When called from the client
	 */
	public static void syncTileEntity(ScriptTileEntity te) throws IllegalAccessException {
		if (ScriptingMod.instance.isClient())
			throw new IllegalAccessException("Can only sync from server");
		Packet p = te.tile.getDescriptionPacket();
		if (p != null) {
			int dim = te.tile.getWorldObj().provider.dimensionId;
			MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayersInDimension(p, dim);
		}
	}
}
