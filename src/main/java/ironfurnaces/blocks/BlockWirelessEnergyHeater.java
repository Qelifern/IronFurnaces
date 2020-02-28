package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockWirelessEnergyHeater extends Block {

    public static final String HEATER = "heater";

    public BlockWirelessEnergyHeater() {
        super(Properties.from(Blocks.IRON_BLOCK));
        this.setDefaultState(this.getDefaultState());
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlockWirelessEnergyHeaterTile();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getTileEntity(pos);
            if (stack.hasDisplayName()) {
                te.setCustomName(stack.getDisplayName());
            }
            if (stack.hasTag() && stack.getTag().getInt("Energy") > 0) {
                te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    h.receiveEnergy(stack.getTag().getInt("Energy"), false);
                });
            }
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            this.interactWith(world, pos, player);
        }
        return true;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
        }
    }

    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof BlockWirelessEnergyHeaterTile) {
                InventoryHelper.dropInventoryItems(world, pos, (BlockWirelessEnergyHeaterTile) te);
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, oldState, p_196243_5_);
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getTileEntity(pos);
            if (te.hasCustomName() || te.getEnergy() > 0) {
                ItemStack itemstack = new ItemStack(Registration.HEATER.get());
                if (te.hasCustomName()) {
                    itemstack.setDisplayName(te.getName());
                }
                itemstack.getOrCreateTag().putInt("Energy", te.getEnergy());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemstack));
            } else {
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Registration.HEATER.get())));
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }
}
