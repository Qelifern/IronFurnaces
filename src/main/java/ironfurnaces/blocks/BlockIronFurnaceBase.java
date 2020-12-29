package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemAugment;
import ironfurnaces.items.ItemAugmentRedstone;
import ironfurnaces.items.ItemSpooky;
import ironfurnaces.items.ItemXmas;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockIronFurnaceBase extends Block {

    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
    public static final IntegerProperty JOVIAL = IntegerProperty.create("jovial", 0, 2);

    public BlockIronFurnaceBase(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.LIT, false).with(TYPE, 0).with(JOVIAL, 0));
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(BlockStateProperties.LIT) ? 14 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return (BlockState) this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) world.getTileEntity(pos);
            if (stack.hasDisplayName()) {
                te.setCustomName(stack.getDisplayName());
            }
        }
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack stack = player.getHeldItem(handIn).copy();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            if (player.getHeldItem(handIn).getItem() instanceof ItemAugment && !(player.isCrouching())) {
                return this.interactAugment(world, pos, player, handIn, stack);
            } else if (player.getHeldItem(handIn).getItem() instanceof ItemSpooky && !(player.isCrouching())) {
                return this.interactJovial(world, pos, player, handIn, 1);
            } else if (player.getHeldItem(handIn).getItem() instanceof ItemXmas && !(player.isCrouching())) {
                return this.interactJovial(world, pos, player, handIn, 2);
            } else if (player.getHeldItem(handIn).isEmpty() && player.isCrouching()) {
                return this.interactJovial(world, pos, player, handIn, 0);
            } else {
                this.interactWith(world, pos, player);
            }
            return ActionResultType.SUCCESS;
        }

    }

    private ActionResultType interactAugment(World world, BlockPos pos, PlayerEntity player, Hand handIn, ItemStack stack) {
        if (!(player.getHeldItem(handIn).getItem() instanceof ItemAugment)) {
            return ActionResultType.SUCCESS;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof BlockIronFurnaceTileBase)) {
            return ActionResultType.SUCCESS;
        }
        if (!(((IInventory) te).getStackInSlot(3).isEmpty())) {
            if (!player.isCreative()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(), ((IInventory) te).getStackInSlot(3));
            }
        }
        ItemStack newStack = new ItemStack(stack.getItem(), 1);
        newStack.setTag(stack.getTag());
        ((IInventory) te).setInventorySlotContents(3, newStack);
        world.playSound(null, te.getPos(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!player.isCreative()) {
            player.getHeldItem(handIn).shrink(1);
        }
        ((BlockIronFurnaceTileBase)te).onUpdateSent();
        return ActionResultType.SUCCESS;
    }
    private ActionResultType interactJovial(World world, BlockPos pos, PlayerEntity player, Hand handIn, int jovial) {
        if (!(player.getHeldItem(handIn).getItem() instanceof ItemSpooky
                || !(player.getHeldItem(handIn).getItem() instanceof ItemXmas)
                || !(player.getHeldItem(handIn).isEmpty()))) {
            return ActionResultType.SUCCESS;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof BlockIronFurnaceTileBase)) {
            return ActionResultType.SUCCESS;
        }
        ((BlockIronFurnaceTileBase)te).setJovial(jovial);
        return ActionResultType.SUCCESS;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            player.addStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.get(BlockStateProperties.LIT)) {
            if (!(world.getTileEntity(pos) instanceof BlockIronFurnaceTileBase))
            {
                return;
            }
            BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase) world.getTileEntity(pos));
            if (tile.getStackInSlot(3).getItem() == Registration.SMOKING_AUGMENT.get())
            {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLOCK_SMOKER_SMOKE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);

            }
            else if (tile.getStackInSlot(3).getItem() == Registration.BLASTING_AUGMENT.get())
            {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                Direction lvt_11_1_ = (Direction)state.get(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis lvt_12_1_ = lvt_11_1_.getAxis();
                double lvt_13_1_ = 0.52D;
                double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
                double lvt_17_1_ = lvt_12_1_ == Direction.Axis.X ? (double)lvt_11_1_.getXOffset() * 0.52D : lvt_15_1_;
                double lvt_19_1_ = rand.nextDouble() * 9.0D / 16.0D;
                double lvt_21_1_ = lvt_12_1_ == Direction.Axis.Z ? (double)lvt_11_1_.getZOffset() * 0.52D : lvt_15_1_;
                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_ + lvt_17_1_, lvt_7_1_ + lvt_19_1_, lvt_9_1_ + lvt_21_1_, 0.0D, 0.0D, 0.0D);

            }
            else
            {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
                world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof BlockIronFurnaceTileBase) {
                InventoryHelper.dropInventoryItems(world, pos, (BlockIronFurnaceTileBase) te);
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, oldState, p_196243_5_);
        }
    }

    public boolean hasComparatorInputOverride(BlockState p_149740_1_) {
        return true;
    }

    public int getComparatorInputOverride(BlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
        return Container.calcRedstone(p_180641_2_.getTileEntity(p_180641_3_));
    }

    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return (BlockState)p_185499_1_.with(BlockStateProperties.HORIZONTAL_FACING, p_185499_2_.rotate((Direction)p_185499_1_.get(BlockStateProperties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.toRotation((Direction)p_185471_1_.get(BlockStateProperties.HORIZONTAL_FACING)));
    }

    private int calculateOutput(ItemStack stack, World worldIn, BlockPos pos, BlockState state) {
        if (!stack.hasTag()) return 0;
        int i = this.getComparatorInputOverride(state, worldIn, pos);
        BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase)worldIn.getTileEntity(pos));
        if (tile != null)
        {
            int j = tile.comparatorSub;
            return stack.getTag().getInt("Mode") == 3 ? Math.max(i - j, 0) : i;
        }
        return 0;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader world, BlockPos pos, Direction side) {
        return getWeakPower(blockState, world, pos, side);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader world, BlockPos pos, Direction side) {
        BlockIronFurnaceTileBase furnace = ((BlockIronFurnaceTileBase) world.getTileEntity(pos));
        if (furnace != null)
        {
            if (furnace.getStackInSlot(3).getItem() instanceof ItemAugmentRedstone)
            {
                if (furnace.getStackInSlot(3).hasTag())
                {
                    if (furnace.getStackInSlot(3).getTag().getInt("Mode") == 0)
                    {
                        return 0;
                    }
                    if (furnace.getStackInSlot(3).getTag().getInt("Mode") == 1)
                    {
                        return 0;
                    }
                    return calculateOutput(furnace.getStackInSlot(3), furnace.getWorld(), pos, blockState);
                }
            }
        }
        return 0;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT, TYPE, JOVIAL);
    }
}
