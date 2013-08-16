package scripting.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import scripting.Selection;
import scripting.network.ScriptPacketHandler;
import scripting.utils.BlockCoord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

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
	public byte[] generatePacket() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(selection.getDimension());
		out.writeBoolean(selection.isEmpty());
		if (!selection.isEmpty()) {
			out.writeBoolean(selection.isEntitySelection());

			if (selection.isEntitySelection())
				out.writeInt(selection.getSelectedEntity().entityId);
			else { 
				out.writeBoolean(selection.isTileSelection());
				if (selection.isTileSelection()) {
					TileEntity te = selection.getSelectedTile();
					writeBlockCoord(out, te.xCoord, te.yCoord, te.zCoord);
				}
				else {
					writeBlockCoord(out, selection.getCorner1());
					out.writeBoolean(selection.getCorner2() != null);
					if (selection.getCorner2() != null)
						writeBlockCoord(out, selection.getCorner2());
				}
			}
		}
		return out.toByteArray();
	}

	private void writeBlockCoord(ByteArrayDataOutput out, BlockCoord coord) {
		out.writeInt(coord.x);
		out.writeInt(coord.y);
		out.writeInt(coord.z);
	}
	
	private void writeBlockCoord(ByteArrayDataOutput out, int x, int y, int z) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	private BlockCoord readBlockCoord(ByteArrayDataInput in) {
		return new BlockCoord(in.readInt(), in.readInt(), in.readInt());
	}

	@Override
	public ScriptPacket readPacket(ByteArrayDataInput pkt) {
		int dim = pkt.readInt();
		if (pkt.readBoolean()) //isEmpty
			selection = new Selection(dim);
		else {
			if (pkt.readBoolean()) {//isEntitySelection
				entityID =  pkt.readInt();
				selection = new Selection(dim);
			}
			else if (pkt.readBoolean()) { //isTileSelection
				tile = readBlockCoord(pkt);
				selection = new Selection(dim);
			}
			else {
				BlockCoord corner1 = readBlockCoord(pkt);
				BlockCoord corner2 = null;
				if (pkt.readBoolean()) //hasCorner2
					corner2 = readBlockCoord(pkt);
				selection = new Selection(dim, corner1, corner2);
			}
		}
		return this;
	}

	@Override
	public void execute(ScriptPacketHandler handler, Player player) {
		handler.handleSelection(this, player);
	}

	public Selection getSelection(Player player) {
		if (entityID != -1)
			selection.setSelectedEntity( ((EntityPlayer)player).worldObj.getEntityByID(entityID) );
		if (tile != null)
			selection.setTileEntity( ((EntityPlayer)player).worldObj.getBlockTileEntity(tile.x, tile.y, tile.z) );
		return selection;
	}

}
