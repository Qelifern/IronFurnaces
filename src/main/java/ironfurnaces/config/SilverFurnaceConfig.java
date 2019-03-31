package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SilverFurnaceConfig {

    public static ForgeConfigSpec.IntValue SPEED;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Silver Furnace");

        SPEED = SERVER_BUILDER
                .comment("Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 140")
                .defineInRange("silver_furnace.speed", 140, 2, 72000);

    }

}
