package ironfurnaces.container;

import ironfurnaces.items.ItemAugment;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceAugment extends Slot {

    private BlockIronFurnaceTileBase te;

    public SlotIronFurnaceAugment(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ItemAugment;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
        te.onUpdateSent();
    }

}
