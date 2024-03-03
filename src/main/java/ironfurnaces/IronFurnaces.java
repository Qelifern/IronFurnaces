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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.TntBlock;
import net.minecraftforge.event.level.ExplosionEvent;
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
    public static final String VERSION = "415";
    public static final String RELEASE_TYPE = "-beta";
    public static final String MC_VERSION = "1.20.1";
    public static final String GITHUB_BRANCH = "1.20.1";

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

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));


        if (Config.checkUpdates.get()) {
            new UpdateChecker();
        } else {
            IronFurnaces.LOGGER.warn("You have disabled Iron Furnaces's Update Checker, to re-enable: change the value of Update Checker in .minecraft->config->ironfurnaces-client.toml to 'true'.");
        }
    }

}
