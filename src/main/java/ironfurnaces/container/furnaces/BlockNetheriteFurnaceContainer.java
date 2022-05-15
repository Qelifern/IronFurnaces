package ironfurnaces.container.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockCopperFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockNetheriteFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockNetheriteFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockNetheriteFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.NETHERITE_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockNetheriteFurnaceTile) world.getBlockEntity(pos);
    }




}
