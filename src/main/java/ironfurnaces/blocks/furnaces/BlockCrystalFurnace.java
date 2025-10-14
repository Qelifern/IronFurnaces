package ironfurnaces.blocks.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockCrystalFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockCrystalFurnace extends BlockIronFurnaceBase implements SimpleWaterloggedBlock {

    public static final String CRYSTAL_FURNACE = "crystal_furnace";
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockCrystalFurnace(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false).setValue(TYPE, 0).setValue(JOVIAL, 0).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, Registration.CRYSTAL_FURNACE_TILE.get());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = pos.getY();
        double d2 = (double) pos.getZ() + 0.5D;

        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        Direction.Axis direction$axis = direction.getAxis();
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
        double d6 = rand.nextDouble() * 6.0D / 16.0D;
        double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
        world.addParticle(ParticleTypes.PORTAL, d0 + d5, d1 + d6 - 0.5D, d2 + d7, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.PORTAL, d0 + d5, d1 + d6 - 0.5D, d2 + d7, 0.0D, 0.0D, 0.0D);

        if (world.getBlockEntity(pos) == null)
        {
            return;
        }
        if (!(world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase tile))
        {
            return;
        }
        if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
            double lvt_5_1_ = (double) pos.getX() + 0.5D;
            double lvt_7_1_ = pos.getY();
            double lvt_9_1_ = (double) pos.getZ() + 0.5D;

            world.addParticle(ParticleTypes.PORTAL, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);
        }

        super.animateTick(state, world, pos, rand);
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        //noinspection deprecation
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos p_153215_, @NotNull BlockState p_153216_) {
        return new BlockCrystalFurnaceTile(p_153215_, p_153216_);
    }
}
