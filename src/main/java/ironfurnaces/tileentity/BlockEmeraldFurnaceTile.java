package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockEmeraldFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockEmeraldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockEmeraldFurnaceTile() {
        super(Registration.EMERALD_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.emeraldFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.emerald_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockEmeraldFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
