package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import ironfurnaces.capability.CapabilityPlayerShowConfig;
import ironfurnaces.util.RainbowEnabledCondition;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(IronFurnaces.MOD_ID) {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.IRON_FURNACE.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        CraftingHelper.register(RainbowEnabledCondition.Serializer.INSTANCE);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        CapabilityPlayerShowConfig.register(event);
    }


}