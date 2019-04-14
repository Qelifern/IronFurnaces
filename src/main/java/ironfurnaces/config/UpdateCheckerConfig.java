package ironfurnaces.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class UpdateCheckerConfig {

    public static ForgeConfigSpec.BooleanValue UPDATE;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Check for Updates");

        UPDATE = CLIENT_BUILDER
                .comment(" true = check for updates, false = don't check for updates.\n Default: true.")
                .define("check_updates.update", true);

    }

}
