package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = IronFurnaces.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(IronFurnaces.MOD_ID) {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.IRON_FURNACE.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {

    }



}