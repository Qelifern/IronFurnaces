package ironfurnaces.container;

import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotIronFurnace extends Slot {

    private final PlayerEntity player;
    private int removeCount;
    private BlockIronFurnaceTileBase te;

    public SlotIronFurnace(PlayerEntity player, BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.player = player;
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack p_75214_1_) {
        return false;
    }

    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        this.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int p_75210_2_) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.te instanceof BlockIronFurnaceTileBase) {
            ((BlockIronFurnaceTileBase)this.te).unlockRecipes(this.player);
        }

        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack) {

    }

}
