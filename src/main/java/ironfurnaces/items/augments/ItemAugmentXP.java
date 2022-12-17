package ironfurnaces.items.augments;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAugmentXP extends ItemAugmentGreen {

    public ItemAugmentXP(Properties properties) {
        super(properties);
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.literal("WIP"));
        //tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".augment_xp").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GOLD))));
        //tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".augment_xp_1").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GOLD))));
        //tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".augment_xp_switch").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GOLD))));
    }
}
