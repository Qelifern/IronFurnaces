package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityGoldFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockGoldFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation GOLD_FURNACE = new ResourceLocation(Main.MOD_ID, "gold_furnace");

    public BlockGoldFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(GOLD_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityGoldFurnace();
    }
}
