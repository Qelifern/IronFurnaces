package ironfurnaces.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {

	PlayerEntity getClientPlayer();

	World getClientWorld();

	World getServerWorld(int dim);

}
