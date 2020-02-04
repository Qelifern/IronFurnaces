package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.BlockObsidianFurnaceContainer;
import ironfurnaces.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockObsidianFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockObsidianFurnaceTile() {
        super(ModBlocks.OBSIDIAN_FURNACE_TYPE);
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.SERVER.obsidianFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.obsidian_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockObsidianFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
