package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRainbowCoal extends Item {

    public ItemRainbowCoal(Properties properties)
    {
        super(properties);
        setRegistryName(IronFurnaces.MOD_ID, "rainbow_coal");
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return ((double) stack.getDamageValue() / (double) 5120);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
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
