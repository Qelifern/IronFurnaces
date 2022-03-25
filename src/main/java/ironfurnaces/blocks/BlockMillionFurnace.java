package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.BlockMillionFurnaceTile;
import net.minecraft.core.BlockPos;
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
