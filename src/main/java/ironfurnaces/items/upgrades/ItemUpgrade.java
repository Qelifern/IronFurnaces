package ironfurnaces.items.upgrades;

import ironfurnaces.IronFurnaces;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.util.FurnaceSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
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

    public ItemUpgrade(Properties properties, Block from, Block to) {
        super(properties);
        this.from = from;
        this.to = to;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            BlockPlaceContext ctx2 = new BlockPlaceContext(ctx);
            if (te.getBlockState().getBlock() != from)
            {
                return InteractionResult.PASS;
            }
            BlockState next = to.getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2) ? to.getStateForPlacement(ctx2) : world.getBlockState(pos);
            if (next == world.getBlockState(pos)) {
                return InteractionResult.PASS;
            }
            if (te instanceof FurnaceBlockEntity) {
                FurnaceBlockEntity furnace = ((FurnaceBlockEntity)te);
                for (int i = 0; i < 3; i++) {
                    ItemStack stack = furnace.getItem(i);
                    Containers.dropItemStack(furnace.getLevel(), (double) furnace.getBlockPos().getX(), (double) furnace.getBlockPos().getY(), (double) furnace.getBlockPos().getZ(), stack);
                }
                world.removeBlockEntity(te.getBlockPos());
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(pos, next, 3);
                world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos),3,  3);
                BlockEntity te2 = world.getBlockEntity(pos);
                if (te2 instanceof BlockIronFurnaceTileBase) {
                    ((BlockIronFurnaceTileBase) te2).placeConfig();
                }
            }
            if (te instanceof BlockIronFurnaceTileBase) {
                int[] FACTORY_COOKTIME = ((BlockIronFurnaceTileBase) te).factoryCookTime;
                int[] FACTORY_TOTALCOOKTIME = ((BlockIronFurnaceTileBase) te).factoryTotalCookTime;
                double[] usedRF = ((BlockIronFurnaceTileBase) te).usedRF;
                double generatorBurn = ((BlockIronFurnaceTileBase) te).generatorBurn;
                int generatorRecentRecipeRF = ((BlockIronFurnaceTileBase) te).generatorRecentRecipeRF;
                double gottenRF = ((BlockIronFurnaceTileBase) te).gottenRF;
                int furnaceBurnTime = ((BlockIronFurnaceTileBase) te).furnaceBurnTime;
                int cookTime = ((BlockIronFurnaceTileBase) te).cookTime;
                int totalCookTime = ((BlockIronFurnaceTileBase) te).totalCookTime;
                int recipesUsed = ((BlockIronFurnaceTileBase) te).recipesUsed;
                FurnaceSettings settings = ((BlockIronFurnaceTileBase) te).furnaceSettings;
                NonNullList<ItemStack> inventory = ((BlockIronFurnaceTileBase) te).inventory;
                world.removeBlockEntity(te.getBlockPos());
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(pos, next, 3);
                BlockEntity newTe = world.getBlockEntity(pos);
                if (newTe instanceof BlockIronFurnaceTileBase)
                {
                    ((BlockIronFurnaceTileBase) newTe).factoryCookTime = FACTORY_COOKTIME;
                    ((BlockIronFurnaceTileBase) newTe).factoryTotalCookTime = FACTORY_TOTALCOOKTIME;
                    ((BlockIronFurnaceTileBase) newTe).usedRF = usedRF;
                    ((BlockIronFurnaceTileBase) newTe).generatorBurn = generatorBurn;
                    ((BlockIronFurnaceTileBase) newTe).generatorRecentRecipeRF = generatorRecentRecipeRF;
                    ((BlockIronFurnaceTileBase) newTe).gottenRF = gottenRF;
                    ((BlockIronFurnaceTileBase) newTe).furnaceBurnTime = furnaceBurnTime;
                    ((BlockIronFurnaceTileBase) newTe).cookTime = cookTime;
                    ((BlockIronFurnaceTileBase) newTe).totalCookTime = totalCookTime;
                    ((BlockIronFurnaceTileBase) newTe).recipesUsed = recipesUsed;
                    ((BlockIronFurnaceTileBase) newTe).furnaceSettings = settings;
                    ((BlockIronFurnaceTileBase) newTe).inventory = inventory;
                }
                world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos),3,  3);
            }
            if (!ctx.getPlayer().isCreative()) {
                ctx.getItemInHand().shrink(1);
            }
        }
        return super.useOn(ctx);
    }
}
