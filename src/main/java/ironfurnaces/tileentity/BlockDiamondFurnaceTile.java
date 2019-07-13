package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockDiamondFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockDiamondFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockDiamondFurnaceTile() {
        super(ModBlocks.DIAMOND_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.diamondFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.diamond_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockDiamondFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
