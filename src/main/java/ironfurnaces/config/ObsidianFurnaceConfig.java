package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ObsidianFurnaceConfig {

    public static ForgeConfigSpec.IntValue SPEED;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Obsidian Furnace");

        SPEED = SERVER_BUILDER
                .comment("Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.")
                .defineInRange("obsidian_furnace.speed", 40, 2, 72000);

    }

}
