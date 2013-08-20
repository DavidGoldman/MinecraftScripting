package scripting.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import org.lwjgl.opengl.GL11;

public class Utils {

	public static final IEntitySelector NO_PLAYERS = new IEntitySelector(){
		@Override
		public boolean isEntityApplicable(Entity entity) {
			return !(entity instanceof EntityPlayer);
		}
	};

	public static final RenderSetting[] RENDER_SETTINGS = new RenderSetting[] {
		new RenderSetting(1f, 0f, 0f, .7f, GL11.GL_LEQUAL),
		new RenderSetting(1f, 0f, 0f, .2f, GL11.GL_GREATER)
	};
	
	public static final RenderSetting[] CORNER1_SETTINGS = new RenderSetting[] {
		new RenderSetting(0f, 1f, 0f, .7f, GL11.GL_LEQUAL),
		new RenderSetting(0f, 1f, 0f, .2f, GL11.GL_GREATER)
	};
	
	public static final RenderSetting[] CORNER2_SETTINGS = new RenderSetting[] {
		new RenderSetting(0f, 0f, 1f, .7f, GL11.GL_LEQUAL),
		new RenderSetting(0f, 0f, 1f, .2f, GL11.GL_GREATER)
	};
	
	
	public static void closeSilently(Closeable c) {
		try { 
			if (c != null)
				c.close(); 

		}
		catch(IOException e) { }
	}

	/**
	 * Gets all tile entities within a Selection AABB.
	 * See {@link net.minecraft.world.WorldServer#getAllTileEntityInBox}.
	 */
	public static List<TileEntity> getTilesInSelectionAABB(World world, AxisAlignedBB selAABB) {
		List<TileEntity> list = new ArrayList<TileEntity>();
		int minX = (int) selAABB.minX;
		int minY = (int) selAABB.minY;
		int minZ = (int) selAABB.minZ;

		int maxX = (int) selAABB.maxX - 1;
		int maxY = (int) selAABB.maxY - 1; 
		int maxZ = (int) selAABB.maxZ - 1;

		for(int x = (minX >> 4); x <= (maxX >> 4); x++) 
			for(int z = (minZ >> 4); z <= (maxZ >> 4); z++) {
				Chunk chunk = world.getChunkFromChunkCoords(x, z);
				if (chunk != null) 
					for(Object obj : chunk.chunkTileEntityMap.values()) {
						TileEntity entity = (TileEntity)obj;
						if (!entity.isInvalid()) 
							if (entity.xCoord >= minX && entity.yCoord >= minY && entity.zCoord >= minZ &&
							entity.xCoord <= maxX && entity.yCoord <= maxY && entity.zCoord <= maxZ)
								list.add(entity);
					}
			}
		return list;
	}
	
	public static int parseIntWithMinMax(String s, int min, int max) throws NumberFormatException {
		int i = Integer.parseInt(s);
		if (i < min)
			return min;
		if (i > max)
			return max;
		return i;

	}

	public static int parseIntWithDMinMax(String s, int _default, int min, int max) {
		try {
			return parseIntWithMinMax(s, min, max);
		} catch (NumberFormatException e) {
			return _default;
		}
	}
	
	public static float parseFloatWithMinMax(String s, float min, float max) throws NumberFormatException {
		float f = Float.parseFloat(s);
		if (f < min)
			return min;
		if (f > max)
			return max;
		return f;
	}

	public static float parseFloatWithDMinMax(String s, float _default, float min, float max) {
		try {
			return parseFloatWithMinMax(s, min, max);
		} catch (NumberFormatException e) {
			return _default;
		}
	}
	
	
	

}
