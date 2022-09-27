package ironfurnaces.container;

import ironfurnaces.container.slots.SlotHeater;
import ironfurnaces.energy.FEnergyStorage;
import ironfurnaces.init.Registration;
import ironfurnaces.items.ItemHeater;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;



public class BlockWirelessEnergyHeaterContainer extends AbstractContainerMenu {


    protected BlockWirelessEnergyHeaterTile te;
    protected Player playerEntity;
    protected IItemHandler playerInventory;
    protected final Level world;

    public BlockWirelessEnergyHeaterContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.HEATER_CONTAINER.get(), windowId);
        this.te = (BlockWirelessEnergyHeaterTile) world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerInventory.player.level;
        trackPower();
        this.addSlot(new SlotHeater(te, 0, 80, 37));
        layoutPlayerInventorySlots(8, 84);

    }

    public int getEnergy() {
        return te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy()
    {
        return te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    // Credit - Mcjty
    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getMaxEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int capacity = h.getMaxEnergyStored() & 0xffff0000;
                    ((FEnergyStorage)h).setCapacity(capacity + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getMaxEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int capacity = h.getMaxEnergyStored() & 0x0000ffff;
                    ((FEnergyStorage)h).setCapacity(capacity | (value << 16));
                });
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((FEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((FEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int pixels) {
        int i = this.getEnergy();
        int j = this.getMaxEnergy();
        return j != 0 && i != 0 ? i * pixels / j : 0;
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
    public ItemStack quickMoveStack(Player playerIn, int index) {
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

    @Override
    public boolean stillValid(Player p_38874_) {
        return this.te.stillValid(p_38874_);
    }

}
