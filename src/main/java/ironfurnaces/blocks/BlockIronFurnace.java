package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityIronFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockIronFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation IRON_FURNACE = new ResourceLocation(Main.MOD_ID, "iron_furnace");

    public BlockIronFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(IRON_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityIronFurnace();
    }
}
