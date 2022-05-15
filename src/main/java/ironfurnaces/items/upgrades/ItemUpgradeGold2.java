package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeGold2 extends ItemUpgrade {


    public ItemUpgradeGold2(Properties properties) {
        super(properties, Registration.SILVER_FURNACE.get(), Registration.GOLD_FURNACE.get());
    }
}
