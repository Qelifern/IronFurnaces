package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import ironfurnaces.util.StringHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemHeater extends Item {


    public ItemHeater(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {


        if (BlockIronFurnaceScreenBase.isShiftKeyDown())
        {
            if (stack.hasTag()) {
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heaterX").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))).append(Component.literal("" + stack.getTag().getInt("X")).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY)))));
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heaterY").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))).append(Component.literal("" + stack.getTag().getInt("Y")).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY)))));
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heaterZ").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))).append(Component.literal("" + stack.getTag().getInt("Z")).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY)))));
            } else {
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater_not_bound").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater_tip").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".heater_tip1").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
            }
        }
        else
        {
            tooltip.add(StringHelper.getShiftInfoText());
        }
    }
}
