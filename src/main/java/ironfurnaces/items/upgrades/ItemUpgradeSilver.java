package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeSilver extends ItemUpgrade {


    public ItemUpgradeSilver(Properties properties) {
        super(properties, Registration.COPPER_FURNACE.get(), Registration.SILVER_FURNACE.get());
    }
}
