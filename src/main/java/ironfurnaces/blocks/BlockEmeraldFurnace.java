package ironfurnaces.blocks;

import ironfurnaces.Main;
import ironfurnaces.init.ModBlocks;
import ironfurnaces.tileentity.BlockEmeraldFurnaceTile;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEmeraldFurnace extends BlockIronFurnaceBase {

    public static final ResourceLocation EMERALD_FURNACE = new ResourceLocation(Main.MOD_ID, "emerald_furnace");

    public BlockEmeraldFurnace(Properties properties) {
        super(properties);
        this.setRegistryName(EMERALD_FURNACE);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) world.getTileEntity(pos);
            if (te.hasCustomName()) {
                ItemStack itemstack = new ItemStack(ModBlocks.emerald_furnace);
                itemstack.setDisplayName(te.getName());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemstack));
            } else {
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.emerald_furnace)));
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockEmeraldFurnaceTile();
    }
}
