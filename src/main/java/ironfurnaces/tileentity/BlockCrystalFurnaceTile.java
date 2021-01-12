package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockCrystalFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockCrystalFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockCrystalFurnaceTile() {
        super(Registration.CRYSTAL_FURNACE_TILE.get());
    }

    @Override
    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.crystalFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.crystal_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockCrystalFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
