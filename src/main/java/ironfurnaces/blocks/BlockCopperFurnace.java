package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityCopperFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockCopperFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation COPPER_FURNACE = new ResourceLocation(Main.MOD_ID, "copper_furnace");

    public BlockCopperFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(COPPER_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityCopperFurnace();
    }
}
