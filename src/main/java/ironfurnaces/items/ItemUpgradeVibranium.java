package ironfurnaces.items;

import ironfurnaces.init.Registration;

public class ItemUpgradeVibranium extends ItemUpgrade {


    public ItemUpgradeVibranium(Properties properties) {
        super(properties, Registration.ALLTHEMODIUM_FURNACE.get(), Registration.VIBRANIUM_FURNACE.get());
    }
}
