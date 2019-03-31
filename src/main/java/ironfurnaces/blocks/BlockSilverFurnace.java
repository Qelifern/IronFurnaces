package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import ironfurnaces.tileentity.TileEntitySilverFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockSilverFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation SILVER_FURNACE = new ResourceLocation(Main.MOD_ID, "silver_furnace");

    public BlockSilverFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(SILVER_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntitySilverFurnace();
    }
}
