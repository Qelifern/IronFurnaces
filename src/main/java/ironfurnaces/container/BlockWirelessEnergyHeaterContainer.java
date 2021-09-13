package ironfurnaces.container;

import ironfurnaces.energy.HeaterEnergyStorage;
import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemHeater;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BlockWirelessEnergyHeaterContainer extends Container {


    protected BlockWirelessEnergyHeaterTile te;
    protected PlayerEntity playerEntity;
    protected IItemHandler playerInventory;
    protected final World world;

    public BlockWirelessEnergyHeaterContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(Registration.HEATER_CONTAINER.get(), windowId);
        this.te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerInventory.player.level;

        this.addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy();
            }

            @Override
            public void set(int value) {
                setEnergy(value);
            }
        });

        this.addSlot(new SlotHeater(te, 0, 80, 37));
        layoutPlayerInventorySlots(8, 84);

    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int pixels) {
        int i = this.getEnergy();
        int j = this.getCapacity();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public int getEnergy() {
        return te.getCapability(CapabilityEnergy.ENERGY).map(h -> h.getEnergyStored()).orElse(0);
    }

    public void setEnergy(int energy) {
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> ((HeaterEnergyStorage)h).setEnergy(energy));
    }

    public int getCapacity() {
        return te.getCapability(CapabilityEnergy.ENERGY).map(h -> h.getMaxEnergyStored()).orElse(0);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(te.getLevel(), te.getBlockPos()), playerEntity, Registration.HEATER.get());
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (!(itemstack.getItem() instanceof ItemHeater))
            {
                return ItemStack.EMPTY;
            }
            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

}
