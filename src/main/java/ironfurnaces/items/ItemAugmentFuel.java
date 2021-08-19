package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAugmentFuel extends ItemAugment {

    public ItemAugmentFuel(Properties properties) {
        super(properties);
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_fuel_pro").setStyle(Style.EMPTY.applyFormat((TextFormatting.GREEN))));
        tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_fuel_con").setStyle(Style.EMPTY.applyFormat(TextFormatting.DARK_RED)));
    }
}
