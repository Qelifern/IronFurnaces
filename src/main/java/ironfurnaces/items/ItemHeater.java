package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.init.ModSetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemHeater extends Item {


    public ItemHeater() {
        super(new Item.Properties().group(ModSetup.ITEM_GROUP).maxStackSize(1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            tooltip.add(new StringTextComponent("Bound to: "));
            tooltip.add(new StringTextComponent("X: " + stack.getTag().getInt("X")));
            tooltip.add(new StringTextComponent("Y: " + stack.getTag().getInt("Y")));
            tooltip.add(new StringTextComponent("Z: " + stack.getTag().getInt("Z")));
        } else {
            tooltip.add(new StringTextComponent("This Wireless Heater has yet to be bound to an energy source!\nOnly works from Iron tier and above."));
        }
    }
}
