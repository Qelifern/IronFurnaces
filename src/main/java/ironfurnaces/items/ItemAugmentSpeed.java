package ironfurnaces.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAugmentSpeed extends ItemAugment {

    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("+Halves the cooktime for all recipes.").setStyle(Style.EMPTY.setFormatting((TextFormatting.GREEN))));
        tooltip.add(new StringTextComponent("-Uses up twice the amount of fuel.").setStyle(Style.EMPTY.setFormatting(TextFormatting.DARK_RED)));
    }
}
