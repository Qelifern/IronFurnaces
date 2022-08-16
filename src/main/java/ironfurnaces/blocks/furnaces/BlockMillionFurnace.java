package ironfurnaces.blocks.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockMillionFurnace extends BlockIronFurnaceBase {

    public static final String MILLION_FURNACE = "million_furnace";


    public BlockMillionFurnace(Properties properties) {
        super(properties);
    }
    public BlockEntity newBlockEntity(BlockPos p_153277_, BlockState p_153278_) {
        return new BlockMillionFurnaceTile(p_153277_, p_153278_);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {
        /*for (Direction direction : Direction.values()) {
            if (Direction.from3DDataValue(direction.get3DDataValue()) != Direction.UP
                    && Direction.from3DDataValue(direction.get3DDataValue()) != Direction.DOWN) {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5D;
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;

                for (int i = 0; i < 10; i++) {
                    world.addParticle(ParticleTypes.CRIT, d0 + d5, d1 + d6, d2 + d7, rand.nextGaussian() * 0.05D, 0.0D, rand.nextGaussian() * 0.05D);
                    world.addParticle(ParticleTypes.CRIMSON_SPORE, d0 + d5, d1 + d6, d2 + d7, rand.nextGaussian() * 0.05D, 0.0D, rand.nextGaussian() * 0.05D);
                }
            }
        }*/
        super.animateTick(state, world, pos, rand);

    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockMillionFurnaceTile tile = (BlockMillionFurnaceTile)world.getBlockEntity(pos);
            for(BlockIronFurnaceTileBase te : tile.furnaces)
            {
                te.linkedPos = new BlockPos(0, 0 , 0);
            }
        }
        super.onRemove(state, world, pos, oldState, p_196243_5_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, Registration.MILLION_FURNACE_TILE.get());
    }
}
