package ironfurnaces.container.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockMillionFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockMillionFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.MILLION_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockMillionFurnaceTile) world.getBlockEntity(pos);
    }





}
