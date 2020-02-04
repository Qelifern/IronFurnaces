package ironfurnaces.proxy;

import ironfurnaces.gui.*;
import ironfurnaces.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy {

	@Override
	public void setup(FMLCommonSetupEvent event) {
		ScreenManager.registerFactory(ModBlocks.IRON_FURNACE_CONTAINER, BlockIronFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.GOLD_FURNACE_CONTAINER, BlockGoldFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.DIAMOND_FURNACE_CONTAINER, BlockDiamondFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.EMERALD_FURNACE_CONTAINER, BlockEmeraldFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.OBSIDIAN_FURNACE_CONTAINER, BlockObsidianFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.COPPER_FURNACE_CONTAINER, BlockCopperFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.SILVER_FURNACE_CONTAINER, BlockSilverFurnaceScreen::new);
		ScreenManager.registerFactory(ModBlocks.HEATER_CONTAINER, BlockWirelessEnergyHeaterScreen::new);
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public World getServerWorld(int dim) {
		return null;
	}



}
