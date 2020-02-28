package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockGoldFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockGoldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockGoldFurnaceTile() {
        super(Registration.GOLD_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.goldFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.gold_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockGoldFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
