package ironfurnaces.blocks;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class BlockWirelessEnergyHeater extends Block implements EntityBlock {

    public static final String HEATER = "heater";

    public BlockWirelessEnergyHeater(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockWirelessEnergyHeaterTile(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(level, type, Registration.HEATER_TILE.get());
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends BlockWirelessEnergyHeaterTile> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, BlockWirelessEnergyHeaterTile::tick);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!world.isClientSide) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
            ItemStack stack = new ItemStack(Registration.HEATER.get());
            if (te.hasCustomName()) {
                stack.setHoverName(te.getDisplayName());
            }
            if (te.getEnergy() > 0) {
                stack.getOrCreateTag().putInt("Energy", te.getEnergy());
            }
            if (!player.isCreative()) Containers.dropItemStack(world, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), stack);
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
            if (stack.hasCustomHoverName()) {
                te.setCustomName(stack.getDisplayName());
            }
            if (stack.hasTag()) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    h.receiveEnergy(stack.getTag().getInt("Energy"), false);
                });
            }
        }
    }

    @Override
    public InteractionResult use(BlockState p_225533_1_, Level world, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
        if (!world.isClientSide) {
            this.interactWith(world, pos, player);
        }
        return InteractionResult.SUCCESS;
    }


    private void interactWith(Level world, BlockPos pos, Player player) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider)tileEntity, tileEntity.getBlockPos());

        }
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof BlockWirelessEnergyHeaterTile) {
                Containers.dropContents(world, pos, (BlockWirelessEnergyHeaterTile) te);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }
}
