package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.BlockNetheriteFurnaceTile;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockNetheriteFurnace extends BlockIronFurnaceBase {

    public static final String NETHERITE_FURNACE = "netherite_furnace";

    public BlockNetheriteFurnace() {
        super(Properties.from(Blocks.DIAMOND_BLOCK).hardnessAndResistance(50.0F, 6000.0F));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.get(BlockStateProperties.LIT)) {
            if (!(world.getTileEntity(pos) instanceof BlockIronFurnaceTileBase))
            {
                return;
            }
            BlockIronFurnaceTileBase tile = ((BlockIronFurnaceTileBase) world.getTileEntity(pos));
            if (tile.getStackInSlot(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
                super.animateTick(state, world, pos, rand);
            } else if (tile.getStackInSlot(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
                super.animateTick(state, world, pos, rand);
            }
            else
            {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
                world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase) world.getTileEntity(pos);
            if (te.hasCustomName()) {
                ItemStack itemstack = new ItemStack(Registration.NETHERITE_FURNACE.get());
                itemstack.setDisplayName(te.getName());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemstack));
            } else {
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Registration.NETHERITE_FURNACE.get())));
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 3;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockNetheriteFurnaceTile();
    }
}
