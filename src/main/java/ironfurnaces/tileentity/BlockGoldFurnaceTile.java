package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockGoldFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockGoldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockGoldFurnaceTile() {
        super(Registration.GOLD_FURNACE_TILE.get());
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.goldFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.gold_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockGoldFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
