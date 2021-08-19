package ironfurnaces.util;

import com.google.common.collect.Lists;
import ironfurnaces.IronFurnaces;
import net.minecraft.util.text.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StringHelper {


    public static List<String> displayEnergy(int energy, int capacity) {
        List<String> text = new ArrayList<String>();
        NumberFormat format = DecimalFormat.getNumberInstance();
        String i = format.format(energy);
        String j = format.format(capacity);
        i = i.replaceAll("\u00A0", ",");
        j = j.replaceAll("\u00A0", ",");
        text.add(i + " / " + j + " RF");
        return text;
    }

    public static List<ITextComponent> getShiftInfoGui()
    {
        List<ITextComponent> list = Lists.newArrayList();
        list.add(new TranslationTextComponent("tooltip.ironfurnaces.gui_close"));
        IFormattableTextComponent tooltip1 = new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_hold_shift");
        IFormattableTextComponent shift = new StringTextComponent("[Shift]");
        IFormattableTextComponent tooltip2 = new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_shift_more_options");
        tooltip1.withStyle(TextFormatting.GRAY);
        shift.withStyle(TextFormatting.GOLD, TextFormatting.ITALIC);
        tooltip2.withStyle(TextFormatting.GRAY);
        list.add(tooltip1.append(shift).append(tooltip2));
        return list;
    }

    public static ITextComponent getShiftInfoText()
    {
        IFormattableTextComponent tooltip1 = new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".hold");
        IFormattableTextComponent shift = new StringTextComponent("[Shift]");
        IFormattableTextComponent tooltip2 = new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".for_details");
        tooltip1.withStyle(TextFormatting.GRAY);
        shift.withStyle(TextFormatting.GOLD, TextFormatting.ITALIC);
        tooltip2.withStyle(TextFormatting.GRAY);
        return tooltip1.append(shift).append(tooltip2);
    }

}
