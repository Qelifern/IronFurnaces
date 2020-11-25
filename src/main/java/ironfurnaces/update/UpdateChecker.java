package ironfurnaces.update;

import ironfurnaces.IronFurnaces;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;


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
            new ThreadUpdateChecker();
            MinecraftForge.EVENT_BUS.register(this);
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Minecraft.getInstance().player != null){
            PlayerEntity player = Minecraft.getInstance().player;
            int id = 0;
            if(UpdateChecker.checkFailed){
                player.sendMessage(ITextComponent.Serializer.getComponentFromJson(I18n.format(IronFurnaces.MOD_ID+".update.failed")), UUID.randomUUID());
            }
            else if(UpdateChecker.needsUpdateNotify){
                player.sendMessage(ITextComponent.Serializer.getComponentFromJson(I18n.format(IronFurnaces.MOD_ID+".update.speech")), UUID.randomUUID());
                player.sendMessage(ITextComponent.Serializer.getComponentFromJson(I18n.format(IronFurnaces.MOD_ID+".update.version", IronFurnaces.MC_VERSION + "-release" + IronFurnaces.VERSION, UpdateChecker.updateVersionString)), UUID.randomUUID());
                player.sendMessage(ITextComponent.Serializer.getComponentFromJson(I18n.format(IronFurnaces.MOD_ID+".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)), UUID.randomUUID());
            }
            if(threadFinished) MinecraftForge.EVENT_BUS.unregister(this);
        }
    }



}
