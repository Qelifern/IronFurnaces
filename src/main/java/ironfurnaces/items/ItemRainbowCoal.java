package ironfurnaces.items;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;

public class ItemRainbowCoal extends Item {

    public ItemRainbowCoal(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int) ((int)13 * (1 - (double) stack.getDamageValue() / (double) 5120));
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        float f = Math.max(0.0F, ((float)5120 - (float)p_150901_.getDamageValue()) / (float)5120);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }


    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(this);
        stack.setDamageValue(this.getDamage(itemStack) + 1);
        if (stack.getDamageValue() >= 5120)
        {
            stack = ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return false;
    }
}
