package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockIronFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockIronFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockIronFurnaceTile() {
        super(ModBlocks.IRON_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.ironFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.iron_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockIronFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
