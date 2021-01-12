package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockEmeraldFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockEmeraldFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockEmeraldFurnaceTile() {
        super(Registration.EMERALD_FURNACE_TILE.get());
    }

    @Override
    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.emeraldFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.emerald_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockEmeraldFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
