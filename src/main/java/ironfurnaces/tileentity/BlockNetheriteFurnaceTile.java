package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockNetheriteFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockNetheriteFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockNetheriteFurnaceTile() {
        super(Registration.NETHERITE_FURNACE_TILE.get());
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.netheriteFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.netherite_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockNetheriteFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
