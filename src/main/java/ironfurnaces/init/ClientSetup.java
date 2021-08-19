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
        ScreenManager.register(Registration.IRON_FURNACE_CONTAINER.get(), BlockIronFurnaceScreen::new);
        ScreenManager.register(Registration.GOLD_FURNACE_CONTAINER.get(), BlockGoldFurnaceScreen::new);
        ScreenManager.register(Registration.DIAMOND_FURNACE_CONTAINER.get(), BlockDiamondFurnaceScreen::new);
        ScreenManager.register(Registration.EMERALD_FURNACE_CONTAINER.get(), BlockEmeraldFurnaceScreen::new);
        ScreenManager.register(Registration.OBSIDIAN_FURNACE_CONTAINER.get(), BlockObsidianFurnaceScreen::new);
        ScreenManager.register(Registration.CRYSTAL_FURNACE_CONTAINER.get(), BlockCrystalFurnaceScreen::new);
        ScreenManager.register(Registration.NETHERITE_FURNACE_CONTAINER.get(), BlockNetheriteFurnaceScreen::new);
        ScreenManager.register(Registration.COPPER_FURNACE_CONTAINER.get(), BlockCopperFurnaceScreen::new);
        ScreenManager.register(Registration.SILVER_FURNACE_CONTAINER.get(), BlockSilverFurnaceScreen::new);
        ScreenManager.register(Registration.MILLION_FURNACE_CONTAINER.get(), BlockMillionFurnaceScreen::new);
        ScreenManager.register(Registration.HEATER_CONTAINER.get(), BlockWirelessEnergyHeaterScreen::new);


        ScreenManager.register(Registration.ALLTHEMODIUM_FURNACE_CONTAINER.get(), BlockAllthemodiumFurnaceScreen::new);
        ScreenManager.register(Registration.VIBRANIUM_FURNACE_CONTAINER.get(), BlockVibraniumFurnaceScreen::new);
        ScreenManager.register(Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), BlockUnobtainiumFurnaceScreen::new);


    }

}