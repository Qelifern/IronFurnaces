package ironfurnaces.blocks;

import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockReader;

public interface IBlockIronFurnace {

    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world);

}
