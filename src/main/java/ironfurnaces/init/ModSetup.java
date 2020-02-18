package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = IronFurnaces.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup(IronFurnaces.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.IRON_FURNACE.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {

    }
}