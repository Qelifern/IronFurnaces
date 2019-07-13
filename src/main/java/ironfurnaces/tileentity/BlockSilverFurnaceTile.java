package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockSilverFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockSilverFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockSilverFurnaceTile() {
        super(ModBlocks.SILVER_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.silverFurnaceSpeed.get();
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
