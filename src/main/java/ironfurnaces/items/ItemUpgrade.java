package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends Item {

    private Block from;
    private Block to;
    boolean one = false;

    public ItemUpgrade(Properties properties, Block from, Block to) {
        super(properties);
        this.from = from;
        this.to = to;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip." + IronFurnaces.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            BlockPlaceContext ctx2 = new BlockPlaceContext(ctx);
            if (te instanceof FurnaceBlockEntity || te instanceof BlockIronFurnaceTileBase) {
                int cooktime = 0;
                int currentItemBurnTime = 0;
                int furnaceBurnTime = 0;
                int[] settings = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                if (te instanceof BlockIronFurnaceTileBase) {
                    furnaceBurnTime = ((BlockIronFurnaceTileBase) te).furnaceBurnTime;
                    currentItemBurnTime = ((BlockIronFurnaceTileBase) te).recipesUsed;
                    cooktime = ((BlockIronFurnaceTileBase) te).cookTime;
                    for (int i = 0; i < ((BlockIronFurnaceTileBase) te).furnaceSettings.size(); i++)
                    {
                        settings[i] = ((BlockIronFurnaceTileBase) te).furnaceSettings.get(i);
                    }

                }
                if (te.getBlockState().getBlock() != from)
                {
                    return InteractionResult.PASS;
                }
                BlockState next = to.getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2) ? to.getStateForPlacement(ctx2) : world.getBlockState(pos);
                if (next == world.getBlockState(pos)) {
                    return InteractionResult.PASS;
                }
                ItemStack input = ((WorldlyContainer) te).getItem(0).copy();
                ItemStack fuel  = ((WorldlyContainer) te).getItem(1).copy();
                ItemStack output  = ((WorldlyContainer) te).getItem(2).copy();
                ItemStack augment  = ItemStack.EMPTY;
                if (te instanceof BlockIronFurnaceTileBase) {
                    augment = ((WorldlyContainer) te).getItem(3).copy();
                }
                world.removeBlockEntity(te.getBlockPos());
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(pos, next, 3);
                BlockEntity newTe = world.getBlockEntity(pos);
                ((WorldlyContainer)newTe).setItem(0, input);
                ((WorldlyContainer)newTe).setItem(1, fuel);
                ((WorldlyContainer)newTe).setItem(2, output);
                if (newTe instanceof BlockIronFurnaceTileBase) {
                    ((WorldlyContainer)newTe).setItem(3, augment);
                    ((BlockIronFurnaceTileBase)newTe).furnaceBurnTime = furnaceBurnTime;
                    ((BlockIronFurnaceTileBase)newTe).recipesUsed = currentItemBurnTime;
                    ((BlockIronFurnaceTileBase)newTe).cookTime = cooktime;
                    for (int i = 0; i < ((BlockIronFurnaceTileBase)newTe).furnaceSettings.size(); i++)
                    {
                        ((BlockIronFurnaceTileBase)newTe).furnaceSettings.set(i, settings[i]);
                    }
                }
                world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos),3,  3);
                if (!ctx.getPlayer().isCreative()) {
                    ctx.getItemInHand().shrink(1);
                }
            }
        }
        return super.useOn(ctx);
    }
}
