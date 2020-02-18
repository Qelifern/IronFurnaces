package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockCopperFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockCopperFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockCopperFurnaceTile() {
        super(Registration.COPPER_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.copperFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.copper_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockCopperFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
