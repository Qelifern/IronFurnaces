package ironfurnaces.mod.tileentity;

import ironfurnaces.mod.block.CopperFurnace;
import ironfurnaces.mod.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCopperFurnace extends TileEntityIronFurnaceBase {
	
	/**
	* This controls whether the tile entity gets replaced whenever the block state 
	* is changed. Normally only want this when block actually is replaced.
	*/
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return !(oldState.getBlock() instanceof CopperFurnace) && !(newState.getBlock() instanceof CopperFurnace);
	}

	@Override
	protected int getCookTime() {
		return Config.COPPER_FURNACE_SPEED;
	}

}
