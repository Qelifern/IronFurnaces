package ironfurnaces.items;

import ironfurnaces.init.Registration;

public class ItemUpgradeSilver extends ItemUpgrade {


    public ItemUpgradeSilver(Properties properties) {
        super(properties, Registration.IRON_FURNACE.get(), Registration.SILVER_FURNACE.get());
    }
}
