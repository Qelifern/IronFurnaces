package ironfurnaces.items.upgrades;

import ironfurnaces.init.Registration;

public class ItemUpgradeCrystal extends ItemUpgrade {


    public ItemUpgradeCrystal(Properties properties) {
        super(properties, Registration.DIAMOND_FURNACE.get(), Registration.CRYSTAL_FURNACE.get());
    }
}
