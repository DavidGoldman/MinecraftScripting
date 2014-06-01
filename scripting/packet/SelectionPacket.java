package scripting.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import scripting.Selection;
import scripting.network.ScriptPacketHandler;
import scripting.utils.BlockCoord;

public class SelectionPacket extends ScriptPacket {

	private Selection selection;
	private int entityID = -1;
	private BlockCoord tile;

	@Override
	public ScriptPacket readData(Object... data) {
		selection = (Selection) data[0];
		return this;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf to) throws IOException { 
		to.writeInt(selection.getDimension());
		to.writeBoolean(selection.isEmpty());
		if (!selection.isEmpty()) {
			to.writeBoolean(selection.isEntitySelection());
			
			if (selection.isEntitySelection())
				to.writeInt(selection.getSelectedEntity().getEntityId());
			else { 
				to.writeBoolean(selection.isTileSelection());
				
				if (selection.isTileSelection()) {
					TileEntity te = selection.getSelectedTile();
					writeBlockCoord(to, te.xCoord, te.yCoord, te.zCoord);
				}
				else {
					writeBlockCoord(to, selection.getCorner1());
					to.writeBoolean(selection.getCorner2() != null);
					
					if (selection.getCorner2() != null)
						writeBlockCoord(to, selection.getCorner2());
				}
			}
		}
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf from) throws IOException {
		int dimension = from.readInt();
		if (from.readBoolean()) //isEmpty
			selection = new Selection(dimension);
		else {
			if (from.readBoolean()) { //isEntitySelection
				entityID =  from.readInt();
				selection = new Selection(dimension);
			}
			else if (from.readBoolean()) { //isTileSelection
				tile = readBlockCoord(from);
				selection = new Selection(dimension);
			}
			else {
				BlockCoord corner1 = readBlockCoord(from);
				BlockCoord corner2 = null;
				if (from.readBoolean()) //hasCorner2
					corner2 = readBlockCoord(from);
				selection = new Selection(dimension, corner1, corner2);
			}
		}
	}

	private void writeBlockCoord(ByteBuf out, BlockCoord coord) {
		out.writeInt(coord.x);
		out.writeInt(coord.y);
		out.writeInt(coord.z);
	}
	
	private void writeBlockCoord(ByteBuf out, int x, int y, int z) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	private BlockCoord readBlockCoord(ByteBuf in) {
		return new BlockCoord(in.readInt(), in.readInt(), in.readInt());
	}

	@Override
	public void execute(ScriptPacketHandler handler, EntityPlayer player) {
		handler.handleSelection(this, player);
	}

	public Selection getSelection(EntityPlayer player) {
		if (entityID != -1)
			selection.setSelectedEntity(player.worldObj.getEntityByID(entityID));
		if (tile != null)
			selection.setTileEntity(player.worldObj.getTileEntity(tile.x, tile.y, tile.z));
		return selection;
	}

}
