package ironfurnaces.container.slots;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotIronFurnace extends Slot {

    private final Player player;
    private int removeCount;
    private BlockIronFurnaceTileBase te;

    public SlotIronFurnace(Player player, BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.player = player;
        this.te = te;
    }

    @Override
    public boolean isActive() {
        return te.getAugmentGUI() == 0 && te.isFurnace();
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean mayPlace(ItemStack p_40231_) {
        return false;
    }

    public void onTake(Player thePlayer, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
    }

    public ItemStack remove(int p_39548_) {
        if (this.hasItem()) {
            this.removeCount += Math.min(p_39548_, this.getItem().getCount());
        }

        return super.remove(p_39548_);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int p_75210_2_) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.te instanceof BlockIronFurnaceTileBase) {
            ((BlockIronFurnaceTileBase)this.te).unlockRecipes((ServerPlayer)this.player);
        }

        this.removeCount = 0;
    }

    protected void checkTakeAchievements(ItemStack p_39558_) {
        p_39558_.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (this.player instanceof ServerPlayer && this.container instanceof BlockIronFurnaceTileBase) {
            ((BlockIronFurnaceTileBase)this.container).unlockRecipes((ServerPlayer)this.player);
        }

        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, p_39558_);
    }

}
