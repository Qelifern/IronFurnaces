package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityEmeraldFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockEmeraldFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation EMERALD_FURNACE = new ResourceLocation(Main.MOD_ID, "emerald_furnace");

    public BlockEmeraldFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(EMERALD_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityEmeraldFurnace();
    }
}
