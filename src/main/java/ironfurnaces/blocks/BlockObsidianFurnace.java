package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import ironfurnaces.tileentity.TileEntityObsidianFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class BlockObsidianFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation OBSIDIAN_FURNACE = new ResourceLocation(Main.MOD_ID, "obsidian_furnace");

    public BlockObsidianFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(OBSIDIAN_FURNACE);
    }

    @Override
    public TileEntityIronFurnaceBase IcreateTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityObsidianFurnace();
    }

    @Override
    public float getExplosionResistance(IBlockState state, IWorldReader world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return 1000F;
    }
}
