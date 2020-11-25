package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockAllthemodiumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockAllthemodiumFurnaceTile() {
        super(Registration.ALLTHEMODIUM_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.allthemodiumFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.allthemodium_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockAllthemodiumFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
