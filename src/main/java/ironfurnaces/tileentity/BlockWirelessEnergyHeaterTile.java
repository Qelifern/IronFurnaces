package ironfurnaces.tileentity;

import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.energy.HeaterEnergyStorage;
import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemHeater;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class BlockWirelessEnergyHeaterTile extends TileEntityInventory implements ITickableTileEntity {


    public BlockWirelessEnergyHeaterTile() {
        super(Registration.HEATER_TILE.get(), 1);
    }

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    private IEnergyStorage createEnergy() {
        return new HeaterEnergyStorage(1000000, 1000000, 0);
    }

    @Override
    public void tick() {
        ItemStack stack = this.getStackInSlot(0);
        if (!stack.isEmpty()) {
            CompoundNBT nbt = new CompoundNBT();
            stack.setTag(nbt);
            nbt.putInt("X", this.pos.getX());
            nbt.putInt("Y", this.pos.getY());
            nbt.putInt("Z", this.pos.getZ());

        }

    }

    public int getEnergy() {
        return this.getCapability(CapabilityEnergy.ENERGY).map(h -> h.getEnergyStored()).orElse(0);
    }

    public int getCapacity() {
        return this.getCapability(CapabilityEnergy.ENERGY).map(h -> h.getMaxEnergyStored()).orElse(0);
    }

    public void removeEnergy(int energy) {
        this.energy.ifPresent(h -> {
            if (h.getEnergyStored() >= energy) {
                ((HeaterEnergyStorage) h).setEnergy(h.getEnergyStored() - energy);
            }
        });
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        energy.ifPresent(h ->{
            ((HeaterEnergyStorage)h).writeToNBT(compound);
        });
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        energy.ifPresent(h ->{
            ((HeaterEnergyStorage)h).readFromNBT(compound);
        });
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
        return "container.wireless_energy_heater";
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof ItemHeater;
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockWirelessEnergyHeaterContainer(i, world, pos, playerInventory, playerEntity);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        if (!this.removed && capability == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(capability, facing);
    }
}
