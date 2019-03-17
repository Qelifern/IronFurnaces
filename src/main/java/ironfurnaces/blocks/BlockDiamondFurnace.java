package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityDiamondFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockDiamondFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation DIAMOND_FURNACE = new ResourceLocation(Main.MOD_ID, "diamond_furnace");

    public BlockDiamondFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(DIAMOND_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityDiamondFurnace();
    }
}
