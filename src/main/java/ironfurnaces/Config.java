package ironfurnaces;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ironfurnaces.init.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.UUID;

@Mod.EventBusSubscriber
public class Config {


    private static boolean run = true;

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_FURNACE = "furnaces";
    public static final String CATEGORY_MODDED_FURNACE = "modded_furnaces";
    public static final String CATEGORY_JEI = "jei";
    public static final String CATEGORY_UPDATES = "updates";
    public static final String CATEGORY_MISC = "misc";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue furnaceXPDropValue;
    public static ForgeConfigSpec.IntValue furnaceXPDropValue2;

    public static ForgeConfigSpec.IntValue ironFurnaceSpeed;
    public static ForgeConfigSpec.IntValue goldFurnaceSpeed;
    public static ForgeConfigSpec.IntValue diamondFurnaceSpeed;
    public static ForgeConfigSpec.IntValue emeraldFurnaceSpeed;
    public static ForgeConfigSpec.IntValue obsidianFurnaceSpeed;
    public static ForgeConfigSpec.IntValue crystalFurnaceSpeed;
    public static ForgeConfigSpec.IntValue netheriteFurnaceSpeed;
    public static ForgeConfigSpec.IntValue copperFurnaceSpeed;
    public static ForgeConfigSpec.IntValue silverFurnaceSpeed;
    public static ForgeConfigSpec.IntValue millionFurnaceSpeed;
    public static ForgeConfigSpec.IntValue  millionFurnacePower;
    public static ForgeConfigSpec.IntValue  millionFurnacePowerToGenerate;
    public static ForgeConfigSpec.BooleanValue enableJeiPlugin;
    public static ForgeConfigSpec.BooleanValue enableJeiCatalysts;
    public static ForgeConfigSpec.BooleanValue enableJeiClickArea;

    public static ForgeConfigSpec.BooleanValue checkUpdates;

    public static ForgeConfigSpec.BooleanValue enableRainbowContent;

    public static ForgeConfigSpec.BooleanValue showErrors;

    //CACHE
    public static ForgeConfigSpec.IntValue cache_capacity;

    //ALLTHEMODS
    public static ForgeConfigSpec.IntValue vibraniumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue unobtaniumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue allthemodiumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue vibraniumFurnaceSmeltMult;
    public static ForgeConfigSpec.IntValue unobtaniumFurnaceSmeltMult;
    public static ForgeConfigSpec.IntValue allthemodiumFurnaceSmeltMult;


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Furnace Settings").push(CATEGORY_FURNACE);

        setupFurnacesConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Modded Furnace Settings").push(CATEGORY_MODDED_FURNACE);

        setupModdedFurnacesConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("JEI Settings").push(CATEGORY_JEI);

        setupJEIConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.pop();


        CLIENT_BUILDER.comment("Misc").push(CATEGORY_MISC);

        enableRainbowContent = CLIENT_BUILDER
                .comment(" Enable or disable the Rainbow Content").define("misc.rainbow", true);

        showErrors = CLIENT_BUILDER
                .comment(" Show furnace settings errors in chat, used for debugging").define("misc.errors", false);


        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Update Checker Settings").push(CATEGORY_UPDATES);

        setupUpdatesConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        cache_capacity = CLIENT_BUILDER
                .comment(" The capacity of the recipe cache, higher values use more memory.\n Default: 10")
                .defineInRange("recipe_cache", 10, 1, 100);

        ironFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 160")
                .defineInRange("iron_furnace.speed", 160, 2, 72000);

        goldFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 120")
                .defineInRange("gold_furnace.speed", 120, 2, 72000);

        diamondFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 80")
                .defineInRange("diamond_furnace.speed", 80, 2, 72000);

        emeraldFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 40")
                .defineInRange("emerald_furnace.speed", 40, 2, 72000);

        obsidianFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20")
                .defineInRange("obsidian_furnace.speed", 20, 2, 72000);

        crystalFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 40")
                .defineInRange("crystal_furnace.speed", 40, 2, 72000);

        netheriteFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 5")
                .defineInRange("netherite_furnace.speed", 5, 2, 72000);

        copperFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 180")
                .defineInRange("copper_furnace.speed", 180, 2, 72000);

        silverFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 140")
                .defineInRange("silver_furnace.speed", 140, 2, 72000);

        millionFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20")
                .defineInRange("rainbow_furnace.speed", 20, 2, 72000);

        millionFurnacePowerToGenerate = CLIENT_BUILDER
                .comment(" How many furnaces that needs to be linked in order for the Rainbow Furnace to generate power.\n Default: 7")
                .defineInRange("rainbow_furnace.power_to_generate", 10000, 1, 100000000);

        millionFurnacePower = CLIENT_BUILDER
                .comment(" How many furnaces that needs to be linked in order for the Rainbow Furnace to generate power.\n Default: 7")
                .defineInRange("rainbow_furnace.power", 7, 1, 100);



        furnaceXPDropValue = CLIENT_BUILDER
                .comment(" This value indicates when the furnace should 'overload' and spit out the xp stored. \n Default: 10, Recipes")
                .defineInRange("furance_xp_drop.value", 10, 1, 500);

        furnaceXPDropValue2 = CLIENT_BUILDER
                .comment(" This value indicates when the furnace should 'overload' and spit out the xp stored. \n Default: 100000, Single recipe uses")
                .defineInRange("furance_xp_drop.value_two", 100000, 1, 1000000);

    }

    private static void setupModdedFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        allthemodiumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 5")
                .defineInRange("allthemodium_furnace.speed", 5, 1, 72000);
        vibraniumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 3")
                .defineInRange("vibranium_furnace.speed", 3, 1, 72000);
        unobtaniumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 1")
                .defineInRange("unobtanium_furnace.speed", 1, 1, 72000);
        allthemodiumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 16")
                .defineInRange("allthemodium_furnace.mult", 16, 1, 64);
        vibraniumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 32")
                .defineInRange("vibranium_furnace.mult", 32, 1, 64);
        unobtaniumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 64")
                .defineInRange("unobtanium_furnace.mult", 64, 1, 64);

    }

    private static void setupJEIConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        enableJeiPlugin = CLIENT_BUILDER
                .comment(" Enable or disable the JeiPlugin of Iron Furnaces.").define("jei.enable_jei", true);

        enableJeiCatalysts = CLIENT_BUILDER
                .comment(" Enable or disable the Catalysts in Jei for Iron Furnaces.").define("jei.enable_jei_catalysts", true);

        enableJeiClickArea = CLIENT_BUILDER
                .comment(" Enable or disable the Click Area inside the GUI in all of Iron Furnaces' furnaces.").define("jei.enable_jei_click_area", true);

    }

    private static void setupUpdatesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        checkUpdates = CLIENT_BUILDER
                .comment(" true = check for updates, false = don't check for updates.\n Default: true.")
                .define("check_updates.updates", true);

    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        IronFurnaces.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        IronFurnaces.LOGGER.debug("Built TOML config for {}", path.toString());
        configData.load();
        IronFurnaces.LOGGER.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {

    }

    @SubscribeEvent
    public static void onWorldLoad(final WorldEvent.Load event) {
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
        run = true;

    }

    @SubscribeEvent
    public static void player(final TickEvent.PlayerTickEvent event) {

        if (!run)
        {
            return;
        }
        if (!event.player.getLevel().isClientSide) {
            if (!((ServerPlayer) event.player).getAdvancements().getOrStartProgress(event.player.getServer().getAdvancements().getAdvancement(new ResourceLocation(IronFurnaces.MOD_ID, "root"))).isDone()) {
            Player player = getPlayer(event.player.getLevel());
            if (player != null && player == event.player) {
                    event.player.level.addFreshEntity(new ItemEntity(event.player.level, event.player.position().x, event.player.position().y, event.player.position().z, new ItemStack(Registration.RAINBOW_COAL.get())));
                    event.player.awardStat(new ResourceLocation(IronFurnaces.MOD_ID, "root"));
                }
            }
        }
        run = false;


    }


    @Nullable
    public static Player getPlayer(Level world) {
        if (world == null) {
            return null;
        }
        try {
            URL newestURL = new URL("https://raw.githubusercontent.com/Qelifern/IronFurnaces/" + IronFurnaces.GITHUB_BRANCH + "/update/uuids.json");
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader(newestURL.openStream()));
            JsonObject rootobj = root.getAsJsonObject();
            JsonArray array = rootobj.get("values").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                if (world.getPlayerByUUID(UUID.fromString(array.get(i).getAsString())) != null) {
                    return world.getPlayerByUUID(UUID.fromString(array.get(i).getAsString()));
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }

}
