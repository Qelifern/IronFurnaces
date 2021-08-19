package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockDiamondFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockDiamondFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockDiamondFurnaceTile() {
        super(Registration.DIAMOND_FURNACE_TILE.get());
    }

    @Override
    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.diamondFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.diamond_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockDiamondFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
