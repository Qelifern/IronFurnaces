package ironfurnaces.container.slots;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnaceInput extends Slot {

    private BlockIronFurnaceTileBase te;

    public SlotIronFurnaceInput(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.te = te;
    }
    @Override
    public boolean mayPlace(ItemStack stack) {
        return te.hasRecipe(stack);
    }

    @Override
    public boolean isActive() {
        return te.getAugmentGUI() == 0 && te.isFurnace();
    }
}
