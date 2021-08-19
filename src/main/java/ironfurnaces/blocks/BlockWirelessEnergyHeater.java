package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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

    public BlockWirelessEnergyHeater(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState());
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
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (!world.isClientSide) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
            ItemStack stack = new ItemStack(Registration.HEATER.get());
            if (te.hasCustomName()) {
                stack.setHoverName(te.getDisplayName());
            }
            if (te.getEnergy() > 0) {
                stack.getOrCreateTag().putInt("Energy", te.getEnergy());
            }
            if (!player.isCreative()) InventoryHelper.dropItemStack(world, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), stack);
        }
        return true;
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
            if (stack.hasCustomHoverName()) {
                te.setCustomName(stack.getDisplayName());
            }
            if (stack.hasTag()) {
                te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    h.receiveEnergy(stack.getTag().getInt("Energy"), false);
                });
            }
        }
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (!world.isClientSide) {
            this.interactWith(world, pos, player);
        }
        return ActionResultType.SUCCESS;
    }


    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getBlockPos());
        }
    }

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof BlockWirelessEnergyHeaterTile) {
                InventoryHelper.dropContents(world, pos, (BlockWirelessEnergyHeaterTile) te);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }
}
