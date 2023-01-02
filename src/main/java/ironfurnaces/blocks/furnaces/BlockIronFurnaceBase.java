package ironfurnaces.blocks.furnaces;

import ironfurnaces.Config;
import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemFurnaceCopy;
import ironfurnaces.items.ItemSpooky;
import ironfurnaces.items.ItemXmas;
import ironfurnaces.items.augments.ItemAugment;
import ironfurnaces.items.augments.ItemAugmentGreen;
import ironfurnaces.items.augments.ItemAugmentRed;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class BlockIronFurnaceBase extends Block implements EntityBlock {

    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
    public static final IntegerProperty JOVIAL = IntegerProperty.create("jovial", 0, 2);

    public BlockIronFurnaceBase(Properties properties) {
        super(properties.destroyTime(3F));
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false).setValue(TYPE, 0).setValue(JOVIAL, 0));
    }

    public MenuProvider getMenuProvider(BlockState p_49234_, Level p_49235_, BlockPos p_49236_) {
        BlockEntity blockentity = p_49235_.getBlockEntity(p_49236_);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        if (Config.disableLightupdates.get())
        {
            return 0;
        }
        return state.getValue(BlockStateProperties.LIT) ? 14 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return (BlockState) this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) world.getBlockEntity(pos);
            if (stack.hasCustomHoverName()) {
                if (!(stack.getDisplayName().getString().contains("[")))
                {
                    te.setCustomName(stack.getDisplayName());
                }
            }
            te.totalCookTime = te.getCookTimeConfig().get();
            te.placeConfig();
            if (entity instanceof Player)
            {
                //te.savedPlayer = ((Player)entity);
            }
        }
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        ItemStack stack = player.getItemInHand(handIn).copy();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player.getItemInHand(handIn).getItem() instanceof ItemAugment && !(player.isCrouching())) {
            return this.interactAugment(world, pos, player, handIn, stack);
        } else if (player.getItemInHand(handIn).getItem() instanceof ItemSpooky && !(player.isCrouching())) {
            return this.interactJovial(world, pos, player, handIn, 1);
        } else if (player.getItemInHand(handIn).getItem() instanceof ItemXmas && !(player.isCrouching())) {
            return this.interactJovial(world, pos, player, handIn, 2);
        } else if (player.getItemInHand(handIn).isEmpty() && player.isCrouching()) {
            return this.interactJovial(world, pos, player, handIn, 0);
        } else if (player.getItemInHand(handIn).getItem() instanceof ItemFurnaceCopy && !(player.isCrouching())) {
            return this.interactCopy(world, pos, player, handIn);
        } else {
            this.interactWith(world, pos, player);
        }
        return InteractionResult.SUCCESS;

    }

    private InteractionResult interactCopy(Level world, BlockPos pos, Player player, InteractionHand handIn) {
        int j = player.getInventory().selected;
        ItemStack stack = player.getInventory().getItem(j);
        if (!(stack.getItem() instanceof ItemFurnaceCopy)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof BlockIronFurnaceTileBase)) {
            return InteractionResult.SUCCESS;
        }

        int[] settings = new int[((BlockIronFurnaceTileBase) te).furnaceSettings.size()];
        for (int i = 0; i < ((BlockIronFurnaceTileBase) te).furnaceSettings.size(); i++)
        {
            settings[i] = ((BlockIronFurnaceTileBase) te).furnaceSettings.get(i);
        }
        stack.getOrCreateTag().putIntArray("settings", settings);

        ((BlockIronFurnaceTileBase)te).onUpdateSent();
        player.sendSystemMessage(Component.literal("Settings copied"));
        return InteractionResult.SUCCESS;
    }
    private InteractionResult interactAugment(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        if (!(player.getItemInHand(handIn).getItem() instanceof ItemAugment)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof BlockIronFurnaceTileBase)) {
            return InteractionResult.SUCCESS;
        }
        int slot = player.getItemInHand(handIn).getItem() instanceof ItemAugmentRed ? 3 : player.getItemInHand(handIn).getItem() instanceof ItemAugmentGreen ? 4 : 5;
        if (!(((WorldlyContainer) te).getItem(slot).isEmpty())) {
            if (!player.isCreative()) {
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), ((WorldlyContainer) te).getItem(slot)));
            }
        }
        ItemStack newStack = new ItemStack(stack.getItem(), 1);
        newStack.setTag(stack.getTag());
        ((WorldlyContainer) te).setItem(slot, newStack);
        world.playSound(null, te.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.isCreative()) {
            player.getItemInHand(handIn).shrink(1);
        }
        ((BlockIronFurnaceTileBase)te).onUpdateSent();
        return InteractionResult.SUCCESS;
    }
    private InteractionResult interactJovial(Level world, BlockPos pos, Player player, InteractionHand handIn, int jovial) {
        if (!(player.getItemInHand(handIn).getItem() instanceof ItemSpooky
                || !(player.getItemInHand(handIn).getItem() instanceof ItemXmas)
                || !(player.getItemInHand(handIn).isEmpty()))) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof BlockIronFurnaceTileBase)) {
            return InteractionResult.SUCCESS;
        }
        ((BlockIronFurnaceTileBase)te).setJovial(jovial);
        return InteractionResult.SUCCESS;
    }

    private void interactWith(Level world, BlockPos pos, Player player) {
        if (!world.isClientSide)
        {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof MenuProvider) {
                NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider)tileEntity, tileEntity.getBlockPos());
                player.awardStat(Stats.INTERACT_WITH_FURNACE);
                if (tileEntity instanceof BlockIronFurnaceTileBase)
                {
                    ((BlockIronFurnaceTileBase) tileEntity).furnaceSettings.set(10, 0);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {
        if (state.getValue(BlockStateProperties.LIT)) {
            if (world.getBlockEntity(pos) == null)
            {
                return;
            }
            if (!(world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase))
            {
                return;
            }
            BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase) world.getBlockEntity(pos));
            if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get())
            {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);

            }
            else if (tile.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get())
            {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

                Direction lvt_11_1_ = (Direction) state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis lvt_12_1_ = lvt_11_1_.getAxis();
                double lvt_13_1_ = 0.52D;
                double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
                double lvt_17_1_ = lvt_12_1_ == Direction.Axis.X ? (double)lvt_11_1_.getStepX() * 0.52D : lvt_15_1_;
                double lvt_19_1_ = rand.nextDouble() * 9.0D / 16.0D;
                double lvt_21_1_ = lvt_12_1_ == Direction.Axis.Z ? (double)lvt_11_1_.getStepZ() * 0.52D : lvt_15_1_;
                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_ + lvt_17_1_, lvt_7_1_ + lvt_19_1_, lvt_9_1_ + lvt_21_1_, 0.0D, 0.0D, 0.0D);

            }
            else
            {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);

                }

                Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
                world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof BlockIronFurnaceTileBase) {

                BlockIronFurnaceTileBase furnace = ((BlockIronFurnaceTileBase)te);
                if (!(furnace instanceof BlockMillionFurnaceTile) && furnace.linkedPos != new BlockPos(0, 0, 0))
                {
                    if (world.getBlockEntity(furnace.linkedPos) instanceof BlockMillionFurnaceTile)
                    {
                        BlockMillionFurnaceTile tile = (BlockMillionFurnaceTile)world.getBlockEntity(furnace.linkedPos);
                        if (tile != null)
                        {
                            tile.furnaces = new ArrayList<BlockIronFurnaceTileBase>();
                        }
                    }

                }


                Containers.dropContents(world, pos, furnace);
                furnace.grantStoredRecipeExperience((ServerLevel) world, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }

    public int getComparatorInputOverride(BlockState state, Level world, BlockPos pos) {
        List<Integer> slots = new ArrayList<>();
        if (world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase)
        {

            BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) world.getBlockEntity(pos);
            if (te.isFurnace()) slots.add(0); slots.add(1); slots.add(2);
            if (te.isGenerator()) slots.add(6);
            if (te.isFactory())
            {
                int tier = te.getTier();
                if (tier >= 0)
                {
                    slots.add(9);
                    slots.add(10);
                    slots.add(15);
                    slots.add(16);
                    if (tier >= 1)
                    {
                        slots.add(8);
                        slots.add(11);
                        slots.add(14);
                        slots.add(17);
                        if (tier >= 2)
                        {
                            slots.add(7);
                            slots.add(12);
                            slots.add(13);
                            slots.add(18);
                        }
                    }

                }
            }
            return getRedstoneSignalFromContainer((WorldlyContainer) world.getBlockEntity(pos), slots);

        }
        return 0;
    }

    public static int getRedstoneSignalFromContainer(@Nullable Container container, List<Integer> slots) {
        if (container == null) {
            return 0;
        } else {
            int i = 0;
            float f = 0.0F;

            for(int slot : slots) {
                ItemStack itemstack = container.getItem(slot);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)Math.min(container.getMaxStackSize(), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f /= (float)slots.size();
            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return (BlockState)p_185499_1_.setValue(BlockStateProperties.HORIZONTAL_FACING, p_185499_2_.rotate((Direction)p_185499_1_.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation((Direction)p_185471_1_.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    private int calculateOutput(Level worldIn, BlockPos pos, BlockState state) {
        BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase)worldIn.getBlockEntity(pos));
        int i = this.getComparatorInputOverride(state, worldIn, pos);
        if (tile != null)
        {
            int j = tile.furnaceSettings.get(9);
            return tile.furnaceSettings.get(8) == 4 ? Math.max(i - j, 0) : i;
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }


    @Override
    public int getSignal(BlockState p_180656_1_, BlockGetter p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
        return getDirectSignal(p_180656_1_, p_180656_2_, p_180656_3_, p_180656_4_);
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction direction) {
        BlockIronFurnaceTileBase furnace = ((BlockIronFurnaceTileBase) world.getBlockEntity(pos));
        if (furnace != null)
        {
            int mode = furnace.furnaceSettings.get(8);
            if (mode == 0)
            {
                return 0;
            }
            else if (mode == 1)
            {
                return 0;
            }
            else if (mode == 2)
            {
                return 0;
            }
            else
            {
                return calculateOutput(furnace.getLevel(), pos, blockState);
            }
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT, TYPE, JOVIAL);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends BlockIronFurnaceTileBase> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, BlockIronFurnaceTileBase::tick);
    }

}
