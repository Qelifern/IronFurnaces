package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockIronFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockIronFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockIronFurnaceTile() {
        super(Registration.IRON_FURNACE_TILE.get());
    }

    @Override
    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.ironFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.iron_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockIronFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
