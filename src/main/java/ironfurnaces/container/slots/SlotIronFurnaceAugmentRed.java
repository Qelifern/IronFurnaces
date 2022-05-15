package ironfurnaces.container.slots;

import ironfurnaces.items.augments.ItemAugmentRed;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceAugmentRed extends Slot {

    private BlockIronFurnaceTileBase te;

    public SlotIronFurnaceAugmentRed(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ItemAugmentRed;
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
