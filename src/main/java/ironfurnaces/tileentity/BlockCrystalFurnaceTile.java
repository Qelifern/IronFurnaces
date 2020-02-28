package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockCrystalFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class BlockCrystalFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockCrystalFurnaceTile() {
        super(Registration.CRYSTAL_FURNACE_TILE.get());
    }

    @Override
    protected int getCookTimeConfig() {
        return Config.crystalFurnaceSpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.crystal_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockCrystalFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
