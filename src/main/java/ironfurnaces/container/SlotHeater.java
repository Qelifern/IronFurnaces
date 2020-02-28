package ironfurnaces.container;

import ironfurnaces.items.ItemHeater;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotHeater extends Slot {

    private BlockWirelessEnergyHeaterTile te;

    public SlotHeater(BlockWirelessEnergyHeaterTile te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemHeater;
    }

}
