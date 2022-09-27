package ironfurnaces.items;

import com.google.common.collect.Lists;
import ironfurnaces.Config;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import ironfurnaces.util.StringHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemMillionFurnace extends BlockItem {
    public ItemMillionFurnace(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    private Random rand = new Random();
    private int timer = 0;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            tooltip.add(Component.literal("Cooktime: " + Config.millionFurnaceSpeed.get()).withStyle(ChatFormatting.GRAY));
            timer++;
            if (timer % 20 == 0) {
                timer = 0;
                String name = Component.translatable("block.ironfurnaces.million_furnace").getString();
                ArrayList<Component> names = Lists.newArrayList();
                for (int i = 0; i < name.length(); i++) {
                    names.add((Component) Component.literal("" + name.charAt(i)).withStyle(ChatFormatting.getById(getIDRandom(rand.nextInt(6)))));
                }
                MutableComponent component = Component.literal("");
                for (int i = 0; i < names.size(); i++) {
                    component.append(names.get(i));
                }
                stack.setHoverName(component);

            }


        if (BlockIronFurnaceScreenBase.isShiftKeyDown())
        {
            Format decimal = new DecimalFormat();
            String part1 = Component.translatable("tooltip.ironfurnaces.rainbow_gen1").getString();
            String part2 = Component.translatable("tooltip.ironfurnaces.rainbow_gen2").getString();
            tooltip.add(Component.literal(part1 + " " + decimal.format(Config.millionFurnacePowerToGenerate.get()).toString().replaceAll("\u00A0", ",") + " " + part2).withStyle(ChatFormatting.GRAY));
        }
        else
        {
            tooltip.add(StringHelper.getShiftInfoText());
        }

    }

    private static int getIDRandom(int id)
    {
        switch (id)
        {
            case 0:
                return 12;
            case 1:
                return 14;
            case 2:
                return 10;
            case 3:
                return 11;
            case 4:
                return 9;
            case 5:
                return 13;
            case 6:
                return 5;
            default:
                return 0;
        }
    }

}
