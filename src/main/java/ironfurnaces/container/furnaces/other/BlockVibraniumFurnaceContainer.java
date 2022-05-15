package ironfurnaces.container.furnaces.other;

import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockVibraniumFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockVibraniumFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.VIBRANIUM_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockVibraniumFurnaceTile) world.getBlockEntity(pos);
    }




}
