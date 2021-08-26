package ironfurnaces.container;

import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotIronFurnaceFuel extends Slot {
    BlockIronFurnaceTileBase te;

    public SlotIronFurnaceFuel(BlockIronFurnaceTileBase te, int index, int x, int y) {
        super(te, index, x, y);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return BlockIronFurnaceTileBase.isItemFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
