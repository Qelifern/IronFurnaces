package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import ironfurnaces.Config;
import ironfurnaces.container.BlockMillionFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import java.util.List;

public class BlockMillionFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockMillionFurnaceTile(BlockPos pos, BlockState state) {
        super(Registration.MILLION_FURNACE_TILE.get(), pos, state);
    }

    public List<BlockIronFurnaceTileBase> furnaces = Lists.newArrayList();
    public List<BlockPos> furnaces_to_load = Lists.newArrayList();

    public boolean providing;

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag furnaces = new CompoundTag();
        for (int i = 0; i < this.furnaces.size(); i++)
        {
            CompoundTag tag2 = new CompoundTag();
            tag2.putInt("X", this.furnaces.get(i).getBlockPos().getX());
            tag2.putInt("Y", this.furnaces.get(i).getBlockPos().getY());
            tag2.putInt("Z", this.furnaces.get(i).getBlockPos().getZ());
            furnaces.put("Furnace" + i, tag2);
        }
        tag.put("Furnaces", furnaces);

    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag furnaces = tag.getCompound("Furnaces");
        for (int i = 0; i < furnaces.size(); i++)
        {
            CompoundTag furnace = furnaces.getCompound("Furnace" + i);
            furnaces_to_load.add(new BlockPos(furnace.getInt("X"), furnace.getInt("Y"), furnace.getInt("Z")));
        }
    }

    @Override
    protected void smeltItem(@Nullable Recipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.getItem(INPUT);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.getItem(OUTPUT);
            int div = 64;
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
                for (int i = 0; i < smelt; i++) {
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
        return Config.millionFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.million_furnace";
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockMillionFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity);
    }


}
