package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockGoldFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockGoldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockGoldFurnaceTile() {
        super(ModBlocks.GOLD_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.goldFurnaceSpeed.get();
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
