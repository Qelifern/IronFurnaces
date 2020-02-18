package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.init.ModSetup;
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

public class ItemAugmentBlasting extends Item {


    public ItemAugmentBlasting() {
        super(new Item.Properties().group(ModSetup.ITEM_GROUP).maxStackSize(1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("+Halves the cooktime for blasting recipes.").setStyle(new Style().setColor(TextFormatting.GREEN)));
            tooltip.add(new StringTextComponent("-Only allows for blasting recipes.").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
    }
}
