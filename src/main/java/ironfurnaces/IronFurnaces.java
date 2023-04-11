package ironfurnaces;

import ironfurnaces.init.ClientSetup;
import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import ironfurnaces.network.Messages;
import ironfurnaces.update.UpdateChecker;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IronFurnaces.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class IronFurnaces
{

    public static final String MOD_ID = "ironfurnaces";
    public static final String VERSION = "380";
    public static final String MC_VERSION = "1.19.4";
    public static final String GITHUB_BRANCH = "1.19.4";

    public static final Logger LOGGER = LogManager.getLogger();

    public static IEventBus MOD_EVENT_BUS;

    public static CreativeModeTab tabIronFurnaces;

    public IronFurnaces() {

        Messages.registerMessages(MOD_ID + "_network");

        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.COMMON_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        MOD_EVENT_BUS.register(Registration.class);

        Registration.init();
        MOD_EVENT_BUS.addListener(this::registerTabs);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));


        if (Config.checkUpdates.get()) {
            new UpdateChecker();
        } else {
            IronFurnaces.LOGGER.warn("You have disabled Iron Furnaces's Update Checker, to re-enable: change the value of Update Checker in .minecraft->config->ironfurnaces-client.toml to 'true'.");
        }
    }


    private void registerTabs(CreativeModeTabEvent.Register event) {
        tabIronFurnaces = event.registerCreativeModeTab(new ResourceLocation(IronFurnaces.MOD_ID, "ironfurnaces"), builder -> builder
                .icon(() -> new ItemStack(Registration.IRON_FURNACE.get()))
                .title(Component.translatable("itemGroup.ironfurnaces"))
                .displayItems((featureFlags, output) -> {
                    output.accept(Registration.IRON_FURNACE_ITEM.get());
                    output.accept(Registration.GOLD_FURNACE_ITEM.get());
                    output.accept(Registration.DIAMOND_FURNACE_ITEM.get());
                    output.accept(Registration.EMERALD_FURNACE_ITEM.get());
                    output.accept(Registration.OBSIDIAN_FURNACE_ITEM.get());
                    output.accept(Registration.CRYSTAL_FURNACE_ITEM.get());
                    output.accept(Registration.NETHERITE_FURNACE_ITEM.get());
                    output.accept(Registration.COPPER_FURNACE_ITEM.get());
                    output.accept(Registration.SILVER_FURNACE_ITEM.get());

                    output.accept(Registration.IRON_UPGRADE.get());
                    output.accept(Registration.GOLD_UPGRADE.get());
                    output.accept(Registration.DIAMOND_UPGRADE.get());
                    output.accept(Registration.EMERALD_UPGRADE.get());
                    output.accept(Registration.OBSIDIAN_UPGRADE.get());
                    output.accept(Registration.CRYSTAL_UPGRADE.get());
                    output.accept(Registration.NETHERITE_UPGRADE.get());
                    output.accept(Registration.COPPER_UPGRADE.get());
                    output.accept(Registration.SILVER_UPGRADE.get());

                    output.accept(Registration.OBSIDIAN2_UPGRADE.get());
                    output.accept(Registration.IRON2_UPGRADE.get());
                    output.accept(Registration.GOLD2_UPGRADE.get());
                    output.accept(Registration.SILVER2_UPGRADE.get());
                    output.accept(Registration.HEATER_ITEM.get());
                    output.accept(Registration.ITEM_HEATER.get());
                    output.accept(Registration.BLASTING_AUGMENT.get());
                    output.accept(Registration.SMOKING_AUGMENT.get());
                    output.accept(Registration.FACTORY_AUGMENT.get());

                    output.accept(Registration.GENERATOR_AUGMENT.get());
                    output.accept(Registration.SPEED_AUGMENT.get());
                    output.accept(Registration.FUEL_AUGMENT.get());
                    output.accept(Registration.XP_AUGMENT.get());
                    output.accept(Registration.ITEM_SPOOKY.get());
                    output.accept(Registration.ITEM_XMAS.get());
                    output.accept(Registration.ITEM_COPY.get());
                    output.accept(Registration.ITEM_LINKER.get());
                    output.accept(Registration.RAINBOW_CORE.get());
                    output.accept(Registration.RAINBOW_PLATING.get());

                    output.accept(Registration.MILLION_FURNACE_ITEM.get());
                    output.accept(Registration.RAINBOW_COAL.get());

                })
        );
    }

}
