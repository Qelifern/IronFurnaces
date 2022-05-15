package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeDiamond extends ItemUpgrade {


    public ItemUpgradeDiamond(Properties properties) {
        super(properties, Registration.GOLD_FURNACE.get(), Registration.DIAMOND_FURNACE.get());
    }
}
