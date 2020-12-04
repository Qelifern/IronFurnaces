package ironfurnaces.items;

import net.minecraft.entity.item.ItemEntity;

import java.util.function.Predicate;

public class UpgradeItems implements Predicate<ItemEntity> {

    @Override
    public boolean test(ItemEntity item) {

        return true;
    }

}
