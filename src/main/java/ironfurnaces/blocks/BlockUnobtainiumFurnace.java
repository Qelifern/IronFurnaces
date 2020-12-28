package ironfurnaces.blocks;

import ironfurnaces.tileentity.BlockUnobtainiumFurnaceTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockUnobtainiumFurnace extends BlockIronFurnaceBase {

    public static final String UNOBTAINIUM_FURNACE = "unobtainium_furnace";

    public BlockUnobtainiumFurnace(Properties properties) {
        super(properties);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockUnobtainiumFurnaceTile();
    }
}
