package ironfurnaces.network;

import io.netty.buffer.ByteBuf;
import ironfurnaces.capability.CapabilityPlayerShowConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketShowConfigButton {

	private int set;

	public PacketShowConfigButton(ByteBuf buf) {
		set = buf.readInt();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(set);
	}

	public PacketShowConfigButton(int set) {
		this.set = set;
	}

	public void handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context ctx = supplier.get();
		ctx.enqueueWork(() -> {
			// Here we are server side
			ServerPlayer player = ctx.getSender();
			player.getCapability(CapabilityPlayerShowConfig.CONFIG).ifPresent(h -> h.set(set));
		});
		ctx.setPacketHandled(true);
	}
}
