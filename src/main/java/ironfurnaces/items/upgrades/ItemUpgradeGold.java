package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeGold extends ItemUpgrade {


    public ItemUpgradeGold(Properties properties) {
        super(properties, Registration.IRON_FURNACE.get(), Registration.GOLD_FURNACE.get());
    }
}
