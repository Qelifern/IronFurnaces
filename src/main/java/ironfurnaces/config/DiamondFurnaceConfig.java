package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DiamondFurnaceConfig {

    public static ForgeConfigSpec.IntValue SPEED;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Diamond Furnace");

        SPEED = SERVER_BUILDER
                .comment("Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.")
                .defineInRange("diamond_furnace.speed", 80, 2, 72000);

    }

}
