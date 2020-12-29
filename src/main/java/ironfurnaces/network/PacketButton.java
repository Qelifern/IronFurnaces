package ironfurnaces.network;

import io.netty.buffer.ByteBuf;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketButton {

	private int x;
	private int y;
	private int z;
	private int set;

	public PacketButton(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		set = buf.readInt();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(set);
	}

	public PacketButton(BlockPos pos, int set) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.set = set;
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			BlockPos pos = new BlockPos(x, y, z);
			BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) player.getServerWorld().getTileEntity(pos);
			if (player.world.isBlockLoaded(pos)) {
				te.comparatorSub+= set;
				te.markDirty();
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
