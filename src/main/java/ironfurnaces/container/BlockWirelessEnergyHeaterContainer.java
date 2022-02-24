package ironfurnaces.container;

import ironfurnaces.energy.HeaterEnergyStorage2;
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
import net.minecraftforge.energy.CapabilityEnergy;
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

        this.addDataSlot(new DataSlot() {
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
        te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> ((HeaterEnergyStorage2)h).setEnergy(energy));
    }

    public int getCapacity() {
        return te.getCapability(CapabilityEnergy.ENERGY).map(h -> h.getMaxEnergyStored()).orElse(0);
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
