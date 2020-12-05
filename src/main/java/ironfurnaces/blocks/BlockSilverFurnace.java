package ironfurnaces.blocks;

import ironfurnaces.tileentity.BlockSilverFurnaceTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockSilverFurnace extends BlockIronFurnaceBase {

    public static final String SILVER_FURNACE = "silver_furnace";

    public BlockSilverFurnace(Properties properties) {
        super(properties);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockSilverFurnaceTile();
    }
}
