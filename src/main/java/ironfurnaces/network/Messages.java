package ironfurnaces.network;

import ironfurnaces.IronFurnaces;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Messages {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(IronFurnaces.MOD_ID, channelName), () -> "1.0", s -> true, s -> true);

        // Server side
        INSTANCE.registerMessage(nextID(), PacketButton.class,
                PacketButton::toBytes,
                PacketButton::new,
                PacketButton::handle);


        // Client side
    }
}
