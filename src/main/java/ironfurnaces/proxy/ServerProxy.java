package ironfurnaces.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ServerProxy implements IProxy {

	@Override
	public PlayerEntity getClientPlayer() {
		throw new IllegalStateException("Can't call this server-side!");
	}

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Can't call this server-side!");
	}


	@Override
	public World getServerWorld(int dim) {
		return Minecraft.getInstance().getIntegratedServer().getWorld(DimensionType.getById(dim)).getWorld();
	}

}
