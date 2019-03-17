package ironfurnaces.container;

import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerIronFurnace extends Container {

    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    TileEntityIronFurnaceBase te;
    InventoryPlayer inventory;

    public ContainerIronFurnace(InventoryPlayer inventory, TileEntityIronFurnaceBase te) {
        this.te = te;
        this.inventory = inventory;
        this.addSlot(new Slot(te, 0, 56, 17));
        this.addSlot(new SlotFurnaceFuel(this.te, 1, 56, 53));
        this.addSlot(new SlotIronFurnace(this.inventory.player, te, 2, 116, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(this.inventory, k, 8 + k * 18, 142));
        }
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.te);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for(IContainerListener icontainerlistener : this.listeners) {
            if (this.cookTime != this.te.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.te.getField(2));
            }

            if (this.furnaceBurnTime != this.te.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.te.getField(0));
            }

            if (this.currentItemBurnTime != this.te.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.te.getField(1));
            }

            if (this.totalCookTime != this.te.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.te.getField(3));
            }
        }

        this.cookTime = this.te.getField(2);
        this.furnaceBurnTime = this.te.getField(0);
        this.currentItemBurnTime = this.te.getField(1);
        this.totalCookTime = this.te.getField(3);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        this.te.setField(id, data);
    }

    @Override
    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    private boolean canSmelt(ItemStack p_206253_1_) {
        for(IRecipe irecipe : this.te.getWorld().getRecipeManager().getRecipes(net.minecraftforge.common.crafting.VanillaRecipeTypes.SMELTING)) {
            if (irecipe.getIngredients().get(0).test(p_206253_1_)) {
                return true;
            }
        }

        return false;
    }
}
