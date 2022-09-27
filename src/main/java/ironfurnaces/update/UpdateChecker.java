package ironfurnaces.update;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


/**
 * Credits: Ellpeck, creator of the Actually Additions update checker, which I (pizzaatime) modified.
 * Link to the Actually Additions repo: https://github.com/Ellpeck/ActuallyAdditions/
 * Link to the Actually Additions curse page: https://minecraft.curseforge.com/projects/actually-additions
 */
@Mod.EventBusSubscriber
public class UpdateChecker {


    public static final String DOWNLOAD_LINK = "https://www.curseforge.com/minecraft/mc-mods/iron-furnaces";
    public static final String CHANGELOG_LINK = "https://raw.githubusercontent.com/Qelifern/IronFurnaces/" + IronFurnaces.MC_VERSION + "/ifchangelog.txt";
    public static boolean checkFailed;
    public static boolean needsUpdateNotify;
    public static int updateVersionInt;
    public static String updateVersionString;
    public static boolean threadFinished = false;

    public UpdateChecker(){
            IronFurnaces.LOGGER.info("Initializing Update Checker...");
            if (!Config.disableWebContent.get())
            {
                new ThreadUpdateChecker();
                MinecraftForge.EVENT_BUS.register(this);
            }

    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Config.disableWebContent.get()) {

            if (Minecraft.getInstance().player != null) {
                Player player = Minecraft.getInstance().player;
                int id = 0;
                if (UpdateChecker.checkFailed) {
                    player.sendSystemMessage(Component.Serializer.fromJson(I18n.get(IronFurnaces.MOD_ID + ".update.failed")));
                } else if (UpdateChecker.needsUpdateNotify) {
                    player.sendSystemMessage(Component.Serializer.fromJson(I18n.get(IronFurnaces.MOD_ID + ".update.speech")));
                    player.sendSystemMessage(Component.Serializer.fromJson(I18n.get(IronFurnaces.MOD_ID + ".update.version", IronFurnaces.MC_VERSION + "-release" + IronFurnaces.VERSION, UpdateChecker.updateVersionString)));
                    player.sendSystemMessage(Component.Serializer.fromJson(I18n.get(IronFurnaces.MOD_ID + ".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)));
                }
                if (threadFinished) MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }



}
