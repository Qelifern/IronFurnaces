package ironfurnaces.blocks;

import ironfurnaces.IronFurnaces;
import ironfurnaces.tileentity.BlockMillionFurnaceTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockMillionFurnace extends BlockIronFurnaceBase {

    public static final String MILLION_FURNACE = "million_furnace";

    public BlockMillionFurnace(Properties properties) {
        super(properties);
        setRegistryName(IronFurnaces.MOD_ID, MILLION_FURNACE);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockMillionFurnaceTile();
    }
}
