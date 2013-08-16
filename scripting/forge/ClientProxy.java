package scripting.forge;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import scripting.Selection;
import scripting.core.ScriptCore;
import scripting.utils.RenderSetting;
import scripting.utils.Utils;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends Proxy {

	public final float XPAN = .010f;

	private Minecraft mc;

	private Selection sel;
	private AxisAlignedBB aabb;
	private List<Entity> entities;
	private List<TileEntity> tiles;
	
	private ClientTickHandler ticker;


	public File getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir;
	}

	public void setSelection(Selection s) { 
		this.sel = s;
		if (sel.isRegionSelection()) {
			AxisAlignedBB pooledBB = s.getAABB();
			this.aabb = AxisAlignedBB.getBoundingBox(pooledBB.minX, pooledBB.minY, pooledBB.minZ, pooledBB.maxX, pooledBB.maxY, pooledBB.maxZ);
			entities = sel.getEntitiesWithinAABB(mc.thePlayer);
			tiles = sel.getTilesWithinAABB(mc.thePlayer);
		}
	}

	public void update() {
		if (sel != null && sel.isRegionSelection()) {
			entities = sel.getEntitiesWithinAABB(mc.thePlayer);
			tiles = sel.getTilesWithinAABB(mc.thePlayer);
		}
	}

	/**
	 * This will only be called from the Minecraft main thread, so we can safely create our tickHandler (which creates the client core).
	 */
	public void postInit(Object... args) {
		ticker = new ClientTickHandler((File) args[0], (Map<String, Object>) args[1], (Map<String, Class<?>>)args[2]);
		TickRegistry.registerScheduledTickHandler(ticker, Side.CLIENT);
		KeyBindingRegistry.registerKeyBinding(new KeyListener());
		mc = Minecraft.getMinecraft();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public ScriptCore getClientCore() {
		return ticker.getClientCore();
	}

	@ForgeSubscribe
	public void renderWorldLast(RenderWorldLastEvent event){
		if (sel != null && !sel.isEmpty() && mc.thePlayer.dimension == sel.getDimension()) {
			if (sel.isEntitySelection()) 
				drawBoundingBox(event.context, event.partialTicks, sel.getSelectedEntity());
			else if (sel.isTileSelection())
				drawBoundingBox(event.context, event.partialTicks, sel.getSelectedTile());
			else if (sel.getCorner2() == null) //aabb = corner1 aabb
				drawBoundingBox(event.context, event.partialTicks, aabb, Utils.CORNER1_SETTINGS);
			else {
				drawBoundingBox(event.context, event.partialTicks, aabb);
				drawBoundingBox(event.context, event.partialTicks, sel.getCorner1().getAABB().expand(XPAN, XPAN, XPAN), Utils.CORNER1_SETTINGS);
				drawBoundingBox(event.context, event.partialTicks, sel.getCorner2().getAABB().expand(XPAN, XPAN, XPAN), Utils.CORNER2_SETTINGS);

				for (Entity e : entities)
					drawBoundingBox(event.context, event.partialTicks, e);
				for (TileEntity t : tiles) 
					drawBoundingBox(event.context, event.partialTicks, t);
			}
		}
	}

	private void drawBoundingBox(RenderGlobal r, float f, TileEntity tile, RenderSetting[] settings) {
		if (tile.worldObj.provider.dimensionId == mc.thePlayer.dimension) {
			int blockID = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord);
			if (blockID > 0) {
				Block.blocksList[blockID].setBlockBoundsBasedOnState(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				AxisAlignedBB aabb = Block.blocksList[blockID].getSelectedBoundingBoxFromPool(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);

				EntityPlayer player = mc.thePlayer;
				double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)f;
				double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)f;
				double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)f;
				drawOutline(r, f, aabb.getOffsetBoundingBox(-playerX, -playerY, -playerZ), settings);
			}
		}
	}

	private void drawBoundingBox(RenderGlobal r, float f, Entity entity, RenderSetting[] settings) {
		if (entity.isEntityAlive() && entity.dimension == sel.getDimension() && entity.boundingBox != null) {
			EntityPlayer player = mc.thePlayer;
			double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)f;
			double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)f;
			double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)f;
			drawOutline(r, f, entity.boundingBox.getOffsetBoundingBox(-playerX, -playerY, -playerZ), settings);
		}
	}

	private void drawBoundingBox(RenderGlobal r, float f, AxisAlignedBB aabb, RenderSetting[] settings) {
		EntityPlayer player = mc.thePlayer;
		double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)f;
		double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)f;
		double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)f;
		aabb = aabb.getOffsetBoundingBox(-playerX, -playerY, -playerZ);
		drawOutline(r, f, aabb, settings);
	}
	
	private void drawBoundingBox(RenderGlobal r, float f, TileEntity tile) {
		drawBoundingBox(r, f, tile, Utils.RENDER_SETTINGS);
	}

	private void drawBoundingBox(RenderGlobal r, float f, Entity entity) {
		drawBoundingBox(r, f, entity, Utils.RENDER_SETTINGS);
	}

	private void drawBoundingBox(RenderGlobal r, float f, AxisAlignedBB aabb) {
		drawBoundingBox(r, f, aabb, Utils.RENDER_SETTINGS);
	}

	private void drawOutline(RenderGlobal r, float partial, AxisAlignedBB aabb, RenderSetting[] settings) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA ,GL11.GL_ONE_MINUS_SRC_ALPHA);

		int originalDepth = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
		Tessellator tes = Tessellator.instance;

		for (RenderSetting s : settings) {
			GL11.glColor4f(s.r, s.g, s.b, s.a);
			GL11.glDepthFunc(s.depthFunc);

			tes.startDrawing(3);
			tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
			tes.draw();
			tes.startDrawing(3);
			tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
			tes.draw();
			tes.startDrawing(1);
			tes.addVertex(aabb.minX, aabb.minY, aabb.minZ);
			tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
			tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
			tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
			tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
			tes.draw();
		}

		GL11.glDepthFunc(originalDepth);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}


}
