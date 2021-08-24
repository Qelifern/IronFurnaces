package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockSilverFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockSilverFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockSilverFurnaceTile() {
        super(Registration.SILVER_FURNACE_TILE.get());
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.silverFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.silver_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockSilverFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
