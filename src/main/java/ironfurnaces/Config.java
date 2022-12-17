package ironfurnaces;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ironfurnaces.init.Registration;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
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
    public static ForgeConfigSpec.IntValue millionFurnacePower;
    public static ForgeConfigSpec.IntValue millionFurnacePowerToGenerate;


    public static ForgeConfigSpec.IntValue ironFurnaceGeneration;
    public static ForgeConfigSpec.IntValue goldFurnaceGeneration;
    public static ForgeConfigSpec.IntValue diamondFurnaceGeneration;
    public static ForgeConfigSpec.IntValue emeraldFurnaceGeneration;
    public static ForgeConfigSpec.IntValue obsidianFurnaceGeneration;
    public static ForgeConfigSpec.IntValue crystalFurnaceGeneration;
    public static ForgeConfigSpec.IntValue netheriteFurnaceGeneration;
    public static ForgeConfigSpec.IntValue copperFurnaceGeneration;
    public static ForgeConfigSpec.IntValue silverFurnaceGeneration;
    public static ForgeConfigSpec.IntValue millionFurnaceGeneration;


    public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier0;
    public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier1;
    public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier2;

    public static ForgeConfigSpec.IntValue ironFurnaceTier;
    public static ForgeConfigSpec.IntValue goldFurnaceTier;
    public static ForgeConfigSpec.IntValue diamondFurnaceTier;
    public static ForgeConfigSpec.IntValue emeraldFurnaceTier;
    public static ForgeConfigSpec.IntValue obsidianFurnaceTier;
    public static ForgeConfigSpec.IntValue crystalFurnaceTier;
    public static ForgeConfigSpec.IntValue netheriteFurnaceTier;
    public static ForgeConfigSpec.IntValue copperFurnaceTier;
    public static ForgeConfigSpec.IntValue silverFurnaceTier;
    public static ForgeConfigSpec.IntValue millionFurnaceTier;

    public static ForgeConfigSpec.IntValue recipeMaxXPLevel;

    public static ForgeConfigSpec.BooleanValue enableJeiPlugin;
    public static ForgeConfigSpec.BooleanValue enableJeiCatalysts;
    public static ForgeConfigSpec.BooleanValue enableJeiClickArea;

    public static ForgeConfigSpec.BooleanValue checkUpdates;

    public static ForgeConfigSpec.BooleanValue enableRainbowContent;

    public static ForgeConfigSpec.BooleanValue showErrors;

    public static ForgeConfigSpec.BooleanValue disableWebContent;
    public static ForgeConfigSpec.BooleanValue disableLightupdates;

    //CACHE
    public static ForgeConfigSpec.IntValue cache_capacity;

    //ALLTHEMODS
    public static ForgeConfigSpec.IntValue vibraniumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue unobtainiumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue allthemodiumFurnaceSpeed;
    public static ForgeConfigSpec.IntValue vibraniumFurnaceSmeltMult;
    public static ForgeConfigSpec.IntValue unobtainiumFurnaceSmeltMult;
    public static ForgeConfigSpec.IntValue allthemodiumFurnaceSmeltMult;

    public static ForgeConfigSpec.IntValue allthemodiumGeneration;
    public static ForgeConfigSpec.IntValue vibraniumGeneration;
    public static ForgeConfigSpec.IntValue unobtainiumGeneration;

    public static ForgeConfigSpec.IntValue allthemodiumFurnaceTier;
    public static ForgeConfigSpec.IntValue vibraniumFurnaceTier;
    public static ForgeConfigSpec.IntValue unobtainiumFurnaceTier;


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Furnace Settings").push(CATEGORY_FURNACE);

        setupFurnacesConfig(COMMON_BUILDER, CLIENT_BUILDER);
        setupGenerationConfig(COMMON_BUILDER, CLIENT_BUILDER);

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

        disableWebContent = CLIENT_BUILDER
                .comment(" Enable or disable version checking and player identification through the web, true = disabled, if your server is using firewall software you might want to disable this").define("misc.web", false);

        disableLightupdates = CLIENT_BUILDER
                .comment(" Enable or disable light-updates, furances will no longer emit light, true = disable").define("misc.lightupdates", false);


        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Update Checker Settings").push(CATEGORY_UPDATES);

        setupUpdatesConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGenerationConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        ironFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 20")
                .defineInRange("iron_furnace.generation", 20, 1, 100000);
        goldFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 80")
                .defineInRange("gold_furnace.generation", 80, 1, 100000);
        diamondFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 120")
                .defineInRange("diamond_furnace.generation", 120, 1, 100000);
        emeraldFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 160")
                .defineInRange("emerald_furnace.generation", 160, 1, 100000);
        obsidianFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 250")
                .defineInRange("obsidian_furnace.generation", 250, 1, 100000);
        crystalFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 180")
                .defineInRange("crystal_furnace.generation", 180, 1, 100000);
        netheriteFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 400")
                .defineInRange("netherite_furnace.generation", 400, 1, 100000);
        millionFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 1000")
                .defineInRange("rainbow_furnace.generation", 1000, 1, 100000);
        copperFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 20")
                .defineInRange("copper_furnace.generation", 20, 1, 100000);
        silverFurnaceGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 50")
                .defineInRange("silver_furnace.generation", 50, 1, 100000);

    }

    private static void setupFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {


        furnaceEnergyCapacityTier0 = CLIENT_BUILDER
                .comment(" How much energy can be stored in tier 0 furnaces.\n Default: 80 000")
                .defineInRange("energy.tier_0", 80000, 4000, Integer.MAX_VALUE);

        furnaceEnergyCapacityTier1 = CLIENT_BUILDER
                .comment(" How much energy can be stored in tier 1 furnaces.\n Default: 200 000")
                .defineInRange("energy.tier_1", 200000, 4000, Integer.MAX_VALUE);

        furnaceEnergyCapacityTier2 = CLIENT_BUILDER
                .comment(" How much energy can be stored in tier 2 furnaces.\n Default: 1 000 000")
                .defineInRange("energy.tier_2", 1000000, 4000, Integer.MAX_VALUE);

        ironFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 0")
                .defineInRange("iron_furnace.tier", 0, 0, 2);

        copperFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 0")
                .defineInRange("copper_furnace.tier", 0, 0, 2);

        goldFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 1")
                .defineInRange("gold_furnace.tier", 1, 0, 2);

        diamondFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 1")
                .defineInRange("diamond_furnace.tier", 1, 0, 2);

        emeraldFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 1")
                .defineInRange("emerald_furnace.tier", 1, 0, 2);

        silverFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 1")
                .defineInRange("silver_furnace.tier", 1, 0, 2);

        crystalFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("crystal_furnace.tier", 2, 0, 2);

        obsidianFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("obsidian_furnace.tier", 2, 0, 2);

        netheriteFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("netherite_furnace.tier", 2, 0, 2);

        millionFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("million_furnace.tier", 2, 0, 2);

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

        millionFurnacePower = CLIENT_BUILDER
                .comment(" How many furnaces that need to be linked in order for the Rainbow Furnace to generate power.\n Default: 9")
                .defineInRange("rainbow_furnace.power", 9, 1, 100);

        millionFurnacePowerToGenerate = CLIENT_BUILDER
                .comment(" How much power the Rainbow Furnace will generate.\n Default: 50000")
                .defineInRange("rainbow_furnace.rainbow_generation", 50000, 1, 100000000);


        recipeMaxXPLevel = CLIENT_BUILDER
                .comment(" How many levels of experience that can be stored in recipes stored in the furnace, after the experience stored in the recipe reaches this value (in levels) it will be voided.\n Default: 100 \n 100 levels is 30971 XP")
                .defineInRange("recipeMaxXPLevel.level", 100, 1, 1000);

    }

    private static void setupModdedFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        allthemodiumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 5")
                .defineInRange("allthemodium_furnace.speed", 5, 1, 72000);
        vibraniumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 3")
                .defineInRange("vibranium_furnace.speed", 3, 1, 72000);
        unobtainiumFurnaceSpeed = CLIENT_BUILDER
                .comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 1")
                .defineInRange("unobtainium_furnace.speed", 1, 1, 72000);
        allthemodiumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 16")
                .defineInRange("allthemodium_furnace.mult", 16, 1, 64);
        vibraniumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 32")
                .defineInRange("vibranium_furnace.mult", 32, 1, 64);
        unobtainiumFurnaceSmeltMult = CLIENT_BUILDER
                .comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 64")
                .defineInRange("unobtainium_furnace.mult", 64, 1, 64);
        allthemodiumGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 1000")
                .defineInRange("allthemodium_furnace.generation", 1000, 1, 100000);
        vibraniumGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 1500")
                .defineInRange("vibranium_furnace.generation", 1500, 1, 100000);
        unobtainiumGeneration = CLIENT_BUILDER
                .comment(" How much RF to generate per tick\n Default: 2000")
                .defineInRange("unobtainium_furnace.generation", 2000, 1, 100000);

        allthemodiumFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("allthemodium_furnace.tier", 2, 0, 2);

        vibraniumFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("vibranium_furnace.tier", 2, 0, 2);

        unobtainiumFurnaceTier = CLIENT_BUILDER
                .comment(" What tier this furnace should be.\n Default: 2")
                .defineInRange("unobtainium_furnace.tier", 2, 0, 2);

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
    public static void onWorldLoad(final LevelEvent.Load event) {
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
        run = true;

    }

    @SubscribeEvent
    public static void player(final TickEvent.PlayerTickEvent event) {

        if (Config.disableWebContent.get())
        {
            return;
        }

        if (!run)
        {
            return;
        }
        if (!event.player.getLevel().isClientSide) {
            if (event.player.getServer().getAdvancements() != null)
            {
                Advancement adv = event.player.getServer().getAdvancements().getAdvancement(new ResourceLocation(IronFurnaces.MOD_ID, "coal"));
                if (adv != null)
                {
                    if (!((ServerPlayer) event.player).getAdvancements().getOrStartProgress(adv).isDone()) {
                        Player player = getPlayer(event.player.getLevel());
                        if (player != null && player == event.player) {
                            event.player.level.addFreshEntity(new ItemEntity(event.player.level, event.player.position().x, event.player.position().y, event.player.position().z, new ItemStack(Registration.RAINBOW_COAL.get())));

                        }
                    }
                }
            }




        }
        run = false;


    }


    @Nullable
    public static Player getPlayer(Level world) {

        if (Config.disableWebContent.get())
        {
            return null;
        }

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
