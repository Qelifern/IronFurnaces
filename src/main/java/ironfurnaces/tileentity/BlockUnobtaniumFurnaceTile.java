package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockUnobtaniumFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockUnobtaniumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockUnobtaniumFurnaceTile() {
        super(Registration.UNOBTANIUM_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.unobtaniumFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.unobtanium_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockUnobtaniumFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
