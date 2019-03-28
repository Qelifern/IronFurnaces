package ironfurnaces.update;

import ironfurnaces.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class UpdateChecker {


    public static final String DOWNLOAD_LINK = "https://minecraft.curseforge.com/projects/iron-furnaces-pizzaatime/files/latest";
    public static final String CHANGELOG_LINK = "https://raw.githubusercontent.com/Qelifern/IronFurnaces/master/ifchangelog.txt";
    public static boolean checkFailed;
    public static boolean needsUpdateNotify;
    public static int updateVersionInt;
    public static String updateVersionString;
    public static boolean threadFinished = false;

    public UpdateChecker(){
            Main.LOGGER.info("Initializing Update Checker...");
            new ThreadUpdateChecker();
            MinecraftForge.EVENT_BUS.register(this);
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        if(Minecraft.getInstance().player != null){
            EntityPlayer player = Minecraft.getInstance().player;
            if(UpdateChecker.checkFailed){
                player.sendMessage(ITextComponent.Serializer.fromJson(I18n.format(Main.MOD_ID+".update.failed")));
            }
            else if(UpdateChecker.needsUpdateNotify){
                player.sendMessage(ITextComponent.Serializer.fromJson(I18n.format(Main.MOD_ID+".update.speech")));
                player.sendMessage(ITextComponent.Serializer.fromJson(I18n.format(Main.MOD_ID+".update.version", Main.VERSION, UpdateChecker.updateVersionString)));
                player.sendMessage(ITextComponent.Serializer.fromJson(I18n.format(Main.MOD_ID+".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)));
            }
            if(threadFinished) MinecraftForge.EVENT_BUS.unregister(this);
        }
    }



}
