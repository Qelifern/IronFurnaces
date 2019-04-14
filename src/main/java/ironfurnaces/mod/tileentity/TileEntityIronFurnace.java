package ironfurnaces.mod.tileentity;

import ironfurnaces.mod.block.IronFurnace;
import ironfurnaces.mod.block.IronFurnaceBase;
import ironfurnaces.mod.config.Config;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileEntityIronFurnace extends TileEntityIronFurnaceBase {

	/**
	* This controls whether the tile entity gets replaced whenever the block state 
	* is changed. Normally only want this when block actually is replaced.
	*/
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return !(oldState.getBlock() instanceof IronFurnace) && !(newState.getBlock() instanceof IronFurnace);
	}

	@Override
	protected int getCookTime() {
		return Config.IRON_FURNACE_SPEED;
	}
}
