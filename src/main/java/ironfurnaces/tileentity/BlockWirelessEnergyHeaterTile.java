package ironfurnaces.tileentity;

import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.energy.FEnergyStorage;
import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemHeater;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class BlockWirelessEnergyHeaterTile extends TileEntityInventory {


    public BlockWirelessEnergyHeaterTile(BlockPos pos, BlockState state) {
        super(Registration.HEATER_TILE.get(), pos, state, 1);
    }

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    private FEnergyStorage createEnergy() {
        return new FEnergyStorage(1000000, 1000000, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }


    public static void tick(Level level, BlockPos worldPosition, BlockState blockState, BlockWirelessEnergyHeaterTile e) {
        ItemStack stack = e.getItem(0);
        if (!stack.isEmpty()) {
            CompoundTag nbt = new CompoundTag();
            stack.setTag(nbt);
            nbt.putInt("X", e.worldPosition.getX());
            nbt.putInt("Y", e.worldPosition.getY());
            nbt.putInt("Z", e.worldPosition.getZ());
        }

    }

    public int getEnergy() {
        return this.getCapability(ForgeCapabilities.ENERGY).map(h -> h.getEnergyStored()).orElse(0);
    }

    public int getCapacity() {
        return this.getCapability(ForgeCapabilities.ENERGY).map(h -> h.getMaxEnergyStored()).orElse(0);
    }

    public void setEnergy(int energy) {
        this.energy.ifPresent(h -> {
            ((FEnergyStorage) h).setEnergy(energy);
        });
    }

    public void setMaxEnergy(int energy) {
        this.energy.ifPresent(h -> {
            ((FEnergyStorage) h).setCapacity(energy);
        });
    }

    public void removeEnergy(int energy) {
        this.energy.ifPresent(h -> {
            ((FEnergyStorage) h).setEnergy(h.getEnergyStored() - energy);
        });
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.energy.ifPresent(h -> {
            ((FEnergyStorage) h).setEnergy(nbt.getInt("Energy"));
        });

    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Energy", getEnergy());
    }

    @Override
    public int[] IgetSlotsForFace(Direction side) {
        return new int[0];
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.wireless_energy_heater";
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof ItemHeater;
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockWirelessEnergyHeaterContainer(i, level, worldPosition, playerInventory, playerEntity);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        //world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
        if (!this.isRemoved() && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        if (!this.isRemoved() && capability == ForgeCapabilities.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        energy.invalidate();
        super.setRemoved();

    }
}
