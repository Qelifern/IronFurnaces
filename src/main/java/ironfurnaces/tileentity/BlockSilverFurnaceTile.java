package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockSilverFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockSilverFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockSilverFurnaceTile() {
        super(Registration.SILVER_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.silverFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.silver_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockSilverFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
