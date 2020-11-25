package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockNetheriteFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockNetheriteFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockNetheriteFurnaceTile() {
        super(Registration.NETHERITE_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.netheriteFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.netherite_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockNetheriteFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
