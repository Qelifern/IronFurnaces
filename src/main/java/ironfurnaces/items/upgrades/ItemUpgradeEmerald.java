package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeEmerald extends ItemUpgrade {


    public ItemUpgradeEmerald(Properties properties) {
        super(properties, Registration.DIAMOND_FURNACE.get(), Registration.EMERALD_FURNACE.get());
    }
}
