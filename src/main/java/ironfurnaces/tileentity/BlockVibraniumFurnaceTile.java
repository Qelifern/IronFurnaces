package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockVibraniumFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockVibraniumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockVibraniumFurnaceTile() {
        super(Registration.VIBRANIUM_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.vibraniumFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.vibranium_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockVibraniumFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
