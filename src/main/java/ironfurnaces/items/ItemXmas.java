package ironfurnaces.items;

import ironfurnaces.init.ModSetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemXmas extends Item {


    public ItemXmas() {
        super(new Properties().group(ModSetup.ITEM_GROUP));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Right-click on furnace to wrap it as a gift!"));
        tooltip.add(new StringTextComponent("But won't the wrappings catch fire?"));
        tooltip.add(new StringTextComponent("Sneaking and right-clicking on a furnace opens the gift"));
    }
}
