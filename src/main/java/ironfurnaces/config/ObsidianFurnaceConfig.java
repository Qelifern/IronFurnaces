package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ObsidianFurnaceConfig {

    public static ForgeConfigSpec.IntValue SPEED;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Obsidian Furnace");

        SPEED = SERVER_BUILDER
                .comment("Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20")
                .defineInRange("obsidian_furnace.speed", 20, 2, 72000);

    }

}
