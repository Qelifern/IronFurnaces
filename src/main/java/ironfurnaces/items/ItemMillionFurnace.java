package ironfurnaces.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMillionFurnace extends BlockItem {
    public ItemMillionFurnace(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //tooltip.add(new StringTextComponent("Will generate X RF/tick if all other furnaces are smelting, \nprovided that they are connected via the Rainbow Linker [NYI]"));
    }
}
