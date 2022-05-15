package ironfurnaces.container.furnaces;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockCopperFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockObsidianFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockSilverFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockObsidianFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockObsidianFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.OBSIDIAN_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
        this.te = (BlockObsidianFurnaceTile) world.getBlockEntity(pos);
    }




}
