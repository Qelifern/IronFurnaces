package ironfurnaces.mod.tileentity;

import ironfurnaces.mod.block.DiamondFurnace;
import ironfurnaces.mod.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDiamondFurnace extends TileEntityIronFurnaceBase {


	/**
	* This controls whether the tile entity gets replaced whenever the block state 
	* is changed. Normally only want this when block actually is replaced.
	*/
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return !(oldState.getBlock() instanceof DiamondFurnace) && !(newState.getBlock() instanceof DiamondFurnace);
	}

	@Override
	protected int getCookTime() {
		return Config.DIAMOND_FURNACE_SPEED;
	}
	
}
