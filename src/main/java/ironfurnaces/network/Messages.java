package ironfurnaces.network;

import ironfurnaces.IronFurnaces;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(IronFurnaces.MOD_ID, channelName), () -> "1.0", s -> true, s -> true);

        // Server side

        INSTANCE.registerMessage(nextID(), PacketSettingsButton.class,
                PacketSettingsButton::toBytes,
                PacketSettingsButton::new,
                PacketSettingsButton::handle);

        INSTANCE.registerMessage(nextID(), PacketShowConfigButton.class,
                PacketShowConfigButton::toBytes,
                PacketShowConfigButton::new,
                PacketShowConfigButton::handle);




        // Client side
    }
}
