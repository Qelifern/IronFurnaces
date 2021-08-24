package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockCopperFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockCopperFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockCopperFurnaceTile() {
        super(Registration.COPPER_FURNACE_TILE.get());
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.copperFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.copper_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockCopperFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
