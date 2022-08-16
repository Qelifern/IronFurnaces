package ironfurnaces.container.slots;

import ironfurnaces.items.ItemHeater;
import ironfurnaces.items.augments.ItemAugmentBlasting;
import ironfurnaces.items.augments.ItemAugmentSmoking;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceInputGenerator extends Slot {

    BlockIronFurnaceTileBase te;
    public SlotIronFurnaceInputGenerator(BlockIronFurnaceTileBase te, int index, int x, int y) {
        super(te, index, x, y);
        this.te = te;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean mayPlace(ItemStack stack) {
        if (!te.getItem(3).isEmpty())
        {
            if (te.getItem(3).getItem() instanceof ItemAugmentBlasting)
            {
                return te.hasGeneratorBlastingRecipe(stack);
            }
            if (te.getItem(3).getItem() instanceof ItemAugmentSmoking)
            {
                return te.getSmokingBurn(stack) > 0;
            }
        }
        if (stack.getItem() instanceof ItemHeater)
        {
            return false;
        }
        return BlockIronFurnaceTileBase.isItemFuel(stack);
    }

    @Override
    public boolean isActive() {
        return te.isGenerator() && te.getAugmentGUI() == 0;
    }
}
