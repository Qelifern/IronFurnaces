package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSpooky extends Item {


    public ItemSpooky(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".spooky_right_click").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
        tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".spooky1").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
        tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".spooky2").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
    }
}
