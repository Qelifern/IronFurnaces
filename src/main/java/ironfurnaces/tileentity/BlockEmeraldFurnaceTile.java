package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockEmeraldFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockEmeraldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockEmeraldFurnaceTile() {
        super(ModBlocks.EMERALD_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.emeraldFurnaceSpeed.get();
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
