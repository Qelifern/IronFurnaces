package ironfurnaces.items;

import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFurnaceCopy extends Item {


    public ItemFurnaceCopy(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            tooltip.add(new StringTextComponent("Down: " + stack.getTag().getIntArray("settings")[0]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Up: " + stack.getTag().getIntArray("settings")[1]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("North: " + stack.getTag().getIntArray("settings")[2]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("South: " + stack.getTag().getIntArray("settings")[3]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("West: " + stack.getTag().getIntArray("settings")[4]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("East: " + stack.getTag().getIntArray("settings")[5]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Auto Input: " + stack.getTag().getIntArray("settings")[6]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Auto Output: " + stack.getTag().getIntArray("settings")[7]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Redstone Mode: " + stack.getTag().getIntArray("settings")[8]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Redstone Value: " + stack.getTag().getIntArray("settings")[9]).setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
        }
        tooltip.add(new StringTextComponent("Right-click to copy settings"));
        tooltip.add(new StringTextComponent("Sneak & right-click to apply settings"));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        if (!ctx.getPlayer().isCrouching())
        {
            return super.onItemUse(ctx);
        }
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);

            if (!(te instanceof BlockIronFurnaceTileBase)) {
                return super.onItemUse(ctx);
            }

            ItemStack stack = ctx.getItem();
            if (stack.hasTag())
            {
                int[] settings = stack.getTag().getIntArray("settings");
                for (int i = 0; i < settings.length; i++)
                ((BlockIronFurnaceTileBase) te).furnaceSettings.set(i, settings[i]);
            }
            world.notifyBlockUpdate(pos, world.getBlockState(pos).getBlock().getDefaultState(), world.getBlockState(pos), 3);
            ctx.getPlayer().sendMessage(new StringTextComponent("Settings applied"), ctx.getPlayer().getUniqueID());
        }

        return super.onItemUse(ctx);
    }
}
