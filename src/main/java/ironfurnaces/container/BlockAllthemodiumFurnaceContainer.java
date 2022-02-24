package ironfurnaces.container;

import ironfurnaces.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;

public class BlockAllthemodiumFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockAllthemodiumFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.SILVER_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }


}
