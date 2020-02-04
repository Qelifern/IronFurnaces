package ironfurnaces.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAugment extends Item {

    public int type;
    public ItemAugment(Properties properties, int type) {
        super(properties);
        this.type = type;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (type == 0) {
            tooltip.add(new StringTextComponent("+Halves the cooktime for blasting recipes.").setStyle(new Style().setColor(TextFormatting.GREEN)));
            tooltip.add(new StringTextComponent("-Only allows for blasting recipes.").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
        } else {
            tooltip.add(new StringTextComponent("+Halves the cooktime for smoking recipes.").setStyle(new Style().setColor(TextFormatting.GREEN)));
            tooltip.add(new StringTextComponent("-Only allows for smoking recipes.").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
        }
    }
}
