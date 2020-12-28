package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockVibraniumFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

import javax.annotation.Nullable;

public class BlockVibraniumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockVibraniumFurnaceTile() {
        super(Registration.VIBRANIUM_FURNACE_TILE.get());
    }

    @Override
    protected void smeltItem(@Nullable IRecipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.inventory.get(INPUT);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.inventory.get(OUTPUT);
            int div = Config.vibraniumFurnaceSmeltMult.get();
            int count = itemstack.getCount() > div ? (itemstack.getCount() - div) : itemstack.getCount();
            int smelt = itemstack1.getCount() > 1 ? 1 : (!itemstack2.isEmpty() && (count + itemstack2.getCount()) > 64 ? (64 - itemstack2.getCount()) : count);
            smelt = smelt > div ? div : smelt;
            if (itemstack2.isEmpty()) {
                this.inventory.set(OUTPUT, new ItemStack(itemstack1.copy().getItem(), smelt));
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount() * smelt);
            }

            if (!this.world.isRemote) {
                for (int i = 0; i < smelt; i++)
                {
                    this.setRecipeUsed(recipe);
                }
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(FUEL).isEmpty() && this.inventory.get(FUEL).getItem() == Items.BUCKET) {
                this.inventory.set(FUEL, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(smelt);
        }
    }

    @Override
    protected int getCookTimeConfig()
    {
        int i = Config.vibraniumFurnaceSpeed.get();
        int j = this.recipeType != null && this.recipeType != IRecipeType.SMELTING ? 2 : 1;
        if (this.world != null)
        {
            int k = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse((i / j));
            if (this.recipeType != null && k < (i / j))
            {
                return k;
            }
        }
        return i / j;
    }

    @Override
    public String IgetName() {
        return "container.vibranium_furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BlockVibraniumFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
    }

}
