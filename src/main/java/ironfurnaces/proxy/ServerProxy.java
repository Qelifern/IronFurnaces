package ironfurnaces.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {

	@Override
	public PlayerEntity getClientPlayer() {
		throw new IllegalStateException("Can't call this server-side!");
	}

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Can't call this server-side!");
	}


}
