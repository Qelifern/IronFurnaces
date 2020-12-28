package ironfurnaces.items;

import ironfurnaces.init.Registration;
import net.minecraft.block.Blocks;

public class ItemUpgradeCopper extends ItemUpgrade {


    public ItemUpgradeCopper(Properties properties) {
        super(properties, Blocks.FURNACE, Registration.COPPER_FURNACE.get());
    }
}
