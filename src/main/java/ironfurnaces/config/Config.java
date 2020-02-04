package ironfurnaces.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import ironfurnaces.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class Config {
    public static final ForgeConfigSpec clientSpec;
    public static final Config.Client CLIENT;
    public static final ForgeConfigSpec serverSpec;
    public static final Config.Server SERVER;

    static {
        final Pair<Config.Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Config.Server::new);
        serverSpec = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Config.Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config.Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        Main.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        Main.LOGGER.debug("Built TOML config for {}", path.toString());
        configData.load();
        Main.LOGGER.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }

    public static class Server {
        public final ForgeConfigSpec.IntValue ironFurnaceSpeed;
        public final ForgeConfigSpec.IntValue goldFurnaceSpeed;
        public final ForgeConfigSpec.IntValue diamondFurnaceSpeed;
        public final ForgeConfigSpec.IntValue emeraldFurnaceSpeed;
        public final ForgeConfigSpec.IntValue obsidianFurnaceSpeed;
        public final ForgeConfigSpec.IntValue copperFurnaceSpeed;
        public final ForgeConfigSpec.IntValue silverFurnaceSpeed;
        public final ForgeConfigSpec.BooleanValue enableJeiPlugin;
        public final ForgeConfigSpec.BooleanValue enableJeiCatalysts;
        public final ForgeConfigSpec.BooleanValue enableJeiClickArea;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server configuration settings")
                    .push("server");

            ironFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 160")
                    .defineInRange("iron_furnace.speed", 160, 2, 72000);

            goldFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 120")
                    .defineInRange("gold_furnace.speed", 120, 2, 72000);

            diamondFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 80")
                    .defineInRange("diamond_furnace.speed", 80, 2, 72000);

            emeraldFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 40")
                    .defineInRange("emerald_furnace.speed", 40, 2, 72000);

            obsidianFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20")
                    .defineInRange("obsidian_furnace.speed", 20, 2, 72000);

            copperFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 180")
                    .defineInRange("copper_furnace.speed", 180, 2, 72000);

            silverFurnaceSpeed = builder
                    .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 140")
                    .defineInRange("silver_furnace.speed", 140, 2, 72000);

            enableJeiPlugin = builder
                    .comment(" Enable or disable the JeiPlugin of Iron Furnaces.").define("jei.enable_jei", true);

            enableJeiCatalysts = builder
                    .comment(" Enable or disable the Catalysts in Jei for Iron Furnaces.").define("jei.enable_jei_catalysts", true);

            enableJeiClickArea = builder
                    .comment(" Enable or disable the Click Area inside the GUI in all of Iron Furnaces' furnaces.").define("jei.enable_jei_click_area", true);

            builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue checkUpdates;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client only settings")
                    .push("client");

            checkUpdates = builder
                    .comment(" true = check for updates, false = don't check for updates.\n Default: true.")
                    .define("check_updates.updates", true);

            builder.pop();
        }
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
        Main.LOGGER.debug("Loaded {} config file {}", Main.MOD_ID, configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
        Main.LOGGER.fatal(CORE, "{} config just got changed on the file system!", Main.MOD_ID);
    }

}
