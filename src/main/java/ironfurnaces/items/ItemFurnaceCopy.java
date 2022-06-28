package ironfurnaces.items;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.hasTag()) {
            if (stack.getTag().getIntArray("settings").length >= 10)
            {
                tooltip.add(Component.literal("Down: " + stack.getTag().getIntArray("settings")[0]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("Up: " + stack.getTag().getIntArray("settings")[1]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("North: " + stack.getTag().getIntArray("settings")[2]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("South: " + stack.getTag().getIntArray("settings")[3]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("West: " + stack.getTag().getIntArray("settings")[4]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("East: " + stack.getTag().getIntArray("settings")[5]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("Auto Input: " + stack.getTag().getIntArray("settings")[6]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("Auto Output: " + stack.getTag().getIntArray("settings")[7]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("Redstone Mode: " + stack.getTag().getIntArray("settings")[8]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
                tooltip.add(Component.literal("Redstone Value: " + stack.getTag().getIntArray("settings")[9]).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
            }
        }
        tooltip.add(Component.literal("Right-click to copy settings").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Sneak & right-click to apply settings").withStyle(ChatFormatting.GRAY));
    }




    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!ctx.getPlayer().isCrouching())
        {
            return super.useOn(ctx);
        }
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);

            if (!(te instanceof BlockIronFurnaceTileBase)) {
                return super.useOn(ctx);
            }

            ItemStack stack = ctx.getItemInHand();
            if (stack.hasTag())
            {
                if (stack.getTag().getIntArray("settings") != null && stack.getTag().getIntArray("settings").length > 0)
                {
                    int[] settings = stack.getTag().getIntArray("settings");
                    for (int i = 0; i < settings.length; i++)
                    {
                        ((BlockIronFurnaceTileBase) te).furnaceSettings.set(i, settings[i]);
                    }
                }
            }
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos), 3, 3);
            ctx.getPlayer().sendSystemMessage(Component.literal("Settings applied"));
        }

        return super.useOn(ctx);
    }
}
