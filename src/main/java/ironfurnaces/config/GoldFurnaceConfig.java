package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoldFurnaceConfig {

    public static ForgeConfigSpec.IntValue SPEED;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Golden Furnace");

        SPEED = SERVER_BUILDER
                .comment("Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 120")
                .defineInRange("gold_furnace.speed", 120, 2, 72000);

    }

}
