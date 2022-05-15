package ironfurnaces.container.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockEmeraldFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockGoldFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockEmeraldFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockEmeraldFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.EMERALD_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockEmeraldFurnaceTile) world.getBlockEntity(pos);
    }






}
