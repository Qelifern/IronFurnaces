package ironfurnaces.tileentity;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public interface ITileInventory {

    public int[] IgetSlotsForFace(Direction side);

    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction);

    public String IgetName();

    public boolean IisItemValidForSlot(int index, ItemStack stack);

    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity);


}
