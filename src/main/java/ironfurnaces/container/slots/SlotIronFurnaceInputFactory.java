package ironfurnaces.container.slots;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceInputFactory extends Slot {

    BlockIronFurnaceTileBase te;
    public int index;
    public SlotIronFurnaceInputFactory(int index, BlockIronFurnaceTileBase te, int slotIndex, int x, int y) {
        super(te, slotIndex, x, y);
        this.te = te;
        this.index = index;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean mayPlace(ItemStack stack) {
        return te.hasRecipe(stack);
    }

    @Override
    public boolean isActive() {
        if (index == 0 || index == 5)
        {
            if (te.getTier() > 1)
            {
                return te.isFactory() && te.getAugmentGUI() == 0;
            }
            else
            {
                return false;
            }
        }
        else if (index == 1 || index == 4)
        {
            if (te.getTier() > 0)
            {
                return te.isFactory() && te.getAugmentGUI() == 0;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return te.isFactory() && te.getAugmentGUI() == 0;
        }
    }
}
