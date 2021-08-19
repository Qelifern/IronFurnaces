package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockCrystalFurnaceTile;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrystalFurnace extends BlockIronFurnaceBase implements IWaterLoggable {

    public static final String CRYSTAL_FURNACE = "crystal_furnace";
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockCrystalFurnace(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false).setValue(TYPE, 0).setValue(JOVIAL, 0).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return (BlockState) this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY();
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
        if (!(world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase))
        {
            return;
        }
        BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase) world.getBlockEntity(pos));
        if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
            double lvt_5_1_ = (double) pos.getX() + 0.5D;
            double lvt_7_1_ = (double) pos.getY();
            double lvt_9_1_ = (double) pos.getZ() + 0.5D;

            world.addParticle(ParticleTypes.PORTAL, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);
        }

        super.animateTick(state, world, pos, rand);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getBlockTicks().scheduleTick(currentPos, Blocks.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockCrystalFurnaceTile();
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }
}
