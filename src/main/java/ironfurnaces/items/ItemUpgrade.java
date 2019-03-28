package ironfurnaces.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemUpgrade extends Item {


    ItemUpgrade(Properties properties) {
        super(properties);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        if (!world.isRemote) {

        }
        return super.onItemUse(ctx);
    }
}
