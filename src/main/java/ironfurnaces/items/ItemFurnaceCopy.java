package ironfurnaces.items;

import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            tooltip.add(new StringTextComponent("Down: " + stack.getTag().getIntArray("settings")[0]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Up: " + stack.getTag().getIntArray("settings")[1]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("North: " + stack.getTag().getIntArray("settings")[2]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("South: " + stack.getTag().getIntArray("settings")[3]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("West: " + stack.getTag().getIntArray("settings")[4]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("East: " + stack.getTag().getIntArray("settings")[5]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Auto Input: " + stack.getTag().getIntArray("settings")[6]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Auto Output: " + stack.getTag().getIntArray("settings")[7]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Redstone Mode: " + stack.getTag().getIntArray("settings")[8]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
            tooltip.add(new StringTextComponent("Redstone Value: " + stack.getTag().getIntArray("settings")[9]).setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
        }
        tooltip.add(new StringTextComponent("Right-click to copy settings"));
        tooltip.add(new StringTextComponent("Sneak & right-click to apply settings"));
    }




    @Override
    public ActionResultType useOn(ItemUseContext ctx) {

        World world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!ctx.getPlayer().isCrouching())
        {
            return super.useOn(ctx);
        }
        if (!world.isClientSide) {
            TileEntity te = world.getBlockEntity(pos);

            if (!(te instanceof BlockIronFurnaceTileBase)) {
                return super.useOn(ctx);
            }

            ItemStack stack = ctx.getItemInHand();
            if (stack.hasTag())
            {
                int[] settings = stack.getTag().getIntArray("settings");
                for (int i = 0; i < settings.length; i++)
                ((BlockIronFurnaceTileBase) te).furnaceSettings.set(i, settings[i]);
            }
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos), 3, 3);
            ctx.getPlayer().sendMessage(new StringTextComponent("Settings applied"), ctx.getPlayer().getUUID());
        }

        return super.useOn(ctx);
    }
}
