package ironfurnaces.tileentity;

import ironfurnaces.Config;
import ironfurnaces.container.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;

public class BlockUnobtainiumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockUnobtainiumFurnaceTile(BlockPos pos, BlockState state) {
        super(Registration.UNOBTAINIUM_FURNACE_TILE.get(), pos, state);
    }

    @Override
    protected void smeltItem(@Nullable Recipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.getItem(INPUT);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.getItem(OUTPUT);
            int div = Config.unobtaniumFurnaceSmeltMult.get();
            int count = itemstack.getCount() > div ? (itemstack.getCount() - div) : itemstack.getCount();
            int smelt = itemstack1.getCount() > 1 ? 1 : (!itemstack2.isEmpty() && (count + itemstack2.getCount()) > 64 ? (64 - itemstack2.getCount()) : count);
            smelt = smelt > div ? div : smelt;
            if (itemstack2.isEmpty()) {
                this.setItem(OUTPUT, new ItemStack(itemstack1.copy().getItem(), smelt));
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount() * smelt);
            }

            this.checkXP(recipe);
            if (!this.level.isClientSide) {
                for (int i = 0; i < smelt; i++)
                {
                    this.setRecipeUsed(recipe);
                }
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.getItem(FUEL).isEmpty() && this.getItem(FUEL).getItem() == Items.BUCKET) {
                this.setItem(FUEL, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(smelt);
        }
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.unobtaniumFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.unobtainium_furnace";
    }



    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockUnobtainiumFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity);
    }

}
