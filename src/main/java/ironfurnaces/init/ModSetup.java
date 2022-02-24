package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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


    @SubscribeEvent
    public void playerEvent(EntityEvent.EntityConstructing e)
    {
        if (e.getEntity() instanceof Player)
        {
            e.getEntity().getEntityData().define(BlockIronFurnaceTileBase.SHOW_CONFIG, 0F);
        }
    }



}