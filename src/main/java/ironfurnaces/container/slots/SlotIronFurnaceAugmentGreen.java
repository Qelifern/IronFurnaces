package ironfurnaces.container.slots;

import ironfurnaces.items.augments.ItemAugment;
import ironfurnaces.items.augments.ItemAugmentGreen;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceAugmentGreen extends Slot {

    private BlockIronFurnaceTileBase te;

    public SlotIronFurnaceAugmentGreen(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ItemAugmentGreen;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
        te.onUpdateSent();
    }

    @Override
    public boolean isActive() {
        return te.getAugmentGUI() == 1;
    }

}
