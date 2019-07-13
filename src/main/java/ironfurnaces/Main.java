package ironfurnaces;

import ironfurnaces.config.Config;
import ironfurnaces.init.ModBlocks;
import ironfurnaces.init.ModItems;
import ironfurnaces.proxy.ClientProxy;
import ironfurnaces.proxy.IProxy;
import ironfurnaces.proxy.ServerProxy;
import ironfurnaces.update.UpdateChecker;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Main
{

    public static final String MOD_ID = "ironfurnaces";
    public static final String VERSION = "150";
    public static final String MC_VERSION = "1.14.3";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ItemGroup itemGroup = new ItemGroup(Main.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.iron_furnace);
        }
    };

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Main() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));

        if (Config.CLIENT.checkUpdates.get()) {
            new UpdateChecker();
        } else {
            Main.LOGGER.warn("You have disabled Iron Furnaces's Update Checker, to re-enable: change the value of Update Checker in .minecraft->config->ironfurnaces-client.toml to 'true'.");
        }

    }

    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
        ModBlocks.registerTiles(event.getRegistry());
    }

    @SubscribeEvent
    public static void config(ConfigChangedEvent.OnConfigChangedEvent event) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
    }

    @SubscribeEvent
    public static void config(ModConfig.ConfigReloading event) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
    }

    @SubscribeEvent
    public static void config(ModConfig.ModConfigEvent event) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        ModBlocks.registerContainers(event.getRegistry());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.log(Level.INFO, "HELLO WORLD");
        proxy.setup(event);
    }
}
