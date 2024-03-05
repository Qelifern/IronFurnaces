package ironfurnaces.init;

import ironfurnaces.capability.CapabilityPlayerFurnacesList;
import ironfurnaces.capability.CapabilityPlayerShowConfig;
import ironfurnaces.util.RainbowEnabledCondition;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {


    public static final Map<Holder.Reference<Item>, Integer> SMOKING_BURNS = new HashMap<>();
    public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE = new HashMap<>();
    public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE_SMOKING = new HashMap<>();
    public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE_BLASTING = new HashMap<>();

    public static void init(final FMLCommonSetupEvent event) {
        CraftingHelper.register(RainbowEnabledCondition.Serializer.INSTANCE);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        CapabilityPlayerShowConfig.register(event);
        CapabilityPlayerFurnacesList.register(event);
    }


}
