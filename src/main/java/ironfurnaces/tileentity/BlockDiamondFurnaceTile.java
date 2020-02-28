package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockDiamondFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockDiamondFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockDiamondFurnaceTile() {
        super(Registration.DIAMOND_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.diamondFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.diamond_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockDiamondFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
