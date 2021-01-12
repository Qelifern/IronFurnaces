package ironfurnaces.blocks;

import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.BlockIronFurnaceScreenBase;
import ironfurnaces.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BlockItemHeater extends BlockItem {


    public BlockItemHeater(Block block, Properties properties) {
        super(block, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag())
        {
            tooltip.add(new StringTextComponent(StringHelper.displayEnergy(stack.getTag().getInt("Energy"), 1000000).get(0)).mergeStyle(TextFormatting.GOLD));
        }
        if (BlockIronFurnaceScreenBase.isShiftKeyDown())
        {
            tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater_block").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater_block1").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
        }
        else
        {
            tooltip.add(StringHelper.getShiftInfoText());
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTag();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasTag())
        {
            int energy = stack.getTag().getInt("Energy");
            return ((double)1 - (double) energy / (double) 1000000);
        }
        return 0;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0xFF800600;
    }
}
