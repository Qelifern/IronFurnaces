package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IronFurnaces.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registration.IRON_FURNACE_CONTAINER.get(), BlockIronFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.GOLD_FURNACE_CONTAINER.get(), BlockGoldFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.DIAMOND_FURNACE_CONTAINER.get(), BlockDiamondFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.EMERALD_FURNACE_CONTAINER.get(), BlockEmeraldFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.OBSIDIAN_FURNACE_CONTAINER.get(), BlockObsidianFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.CRYSTAL_FURNACE_CONTAINER.get(), BlockCrystalFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.COPPER_FURNACE_CONTAINER.get(), BlockCopperFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.SILVER_FURNACE_CONTAINER.get(), BlockSilverFurnaceScreen::new);
        ScreenManager.registerFactory(Registration.HEATER_CONTAINER.get(), BlockWirelessEnergyHeaterScreen::new);
    }

}