package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.BlockIronFurnaceScreenBase;
import ironfurnaces.util.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemHeater extends Item {


    public ItemHeater(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        if (BlockIronFurnaceScreenBase.isShiftKeyDown())
        {
            if (stack.hasTag()) {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heaterX").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))).append(new StringTextComponent("" + stack.getTag().getInt("X")).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY)))));
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heaterY").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))).append(new StringTextComponent("" + stack.getTag().getInt("Y")).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY)))));
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heaterZ").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))).append(new StringTextComponent("" + stack.getTag().getInt("Z")).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY)))));
            } else {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater_not_bound").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater_tip").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".heater_tip1").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            }
        }
        else
        {
            tooltip.add(StringHelper.getShiftInfoText());
        }
    }
}
