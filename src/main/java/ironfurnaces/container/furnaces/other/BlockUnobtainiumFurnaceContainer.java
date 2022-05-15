package ironfurnaces.container.furnaces.other;

import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockCopperFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockUnobtainiumFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockUnobtainiumFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockUnobtainiumFurnaceTile) world.getBlockEntity(pos);
    }




}
