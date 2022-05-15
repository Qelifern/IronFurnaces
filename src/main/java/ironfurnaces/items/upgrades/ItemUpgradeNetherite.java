package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeNetherite extends ItemUpgrade {


    public ItemUpgradeNetherite(Properties properties) {
        super(properties, Registration.OBSIDIAN_FURNACE.get(), Registration.NETHERITE_FURNACE.get());
    }
}
