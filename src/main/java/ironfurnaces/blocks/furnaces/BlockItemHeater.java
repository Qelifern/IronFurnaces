package ironfurnaces.blocks.furnaces;

import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import ironfurnaces.util.StringHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BlockItemHeater extends BlockItem {


    public BlockItemHeater(Block block, Properties properties) {
        super(block, properties);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.hasTag()) {
            tooltip.add(Component.literal(StringHelper.displayEnergy(stack.getTag().getInt("Energy"), 1000000).get(0)).withStyle(ChatFormatting.GOLD));
        }
        if (BlockIronFurnaceScreenBase.isShiftKeyDown()) {
            tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater_block").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
            tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater_block1").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
        } else {
            tooltip.add(StringHelper.getShiftInfoText());
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.hasTag();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (stack.hasTag())
        {
            int energy = stack.getTag().getInt("Energy");
            return (int) ((int)13 * ((double) energy / (double) 1000000));
        }
        return 0;
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        return 0xFF800600;
    }
}
