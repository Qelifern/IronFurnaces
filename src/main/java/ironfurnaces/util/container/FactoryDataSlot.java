package ironfurnaces.util.container;

import net.minecraft.world.inventory.DataSlot;

public abstract class FactoryDataSlot extends DataSlot {

    public int index;
    public FactoryDataSlot(int index)
    {
        this.index = index;
    }
}
