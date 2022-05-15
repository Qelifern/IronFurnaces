package ironfurnaces.container.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockGoldFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockGoldFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockGoldFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.GOLD_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockGoldFurnaceTile) world.getBlockEntity(pos);
    }




}
