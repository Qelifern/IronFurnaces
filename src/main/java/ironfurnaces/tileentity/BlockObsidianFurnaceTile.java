package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockObsidianFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockObsidianFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockObsidianFurnaceTile() {
        super(Registration.OBSIDIAN_FURNACE_TILE.get());
    }

    @Override
    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.obsidianFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.obsidian_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockObsidianFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
