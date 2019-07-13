package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockCopperFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockCopperFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockCopperFurnaceTile() {
        super(ModBlocks.COPPER_FURNACE_TYPE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.copperFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.copper_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockCopperFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
