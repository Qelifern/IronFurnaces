package ironfurnaces.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import ironfurnaces.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class Config {

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        UpdateCheckerConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        IronFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        GoldFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        DiamondFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        EmeraldFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        ObsidianFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        CopperFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        SilverFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
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

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        Main.LOGGER.debug("Loaded {} config file {}", Main.MOD_ID, configEvent.getConfig().getFileName());

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
        Main.LOGGER.fatal(CORE, "{} config just got changed on the file system!", Main.MOD_ID);
    }

}
