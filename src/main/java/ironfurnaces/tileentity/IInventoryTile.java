package ironfurnaces.tileentity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IInventoryTile {

    public int[] IgetSlotsForFace(EnumFacing side);

    public String IgetGuiID();

    public boolean IcanExtractItem(int index, ItemStack stack, EnumFacing direction);

    public String IgetName();

    public int IgetFieldCount();

    public void IsetField(int id, int value);

    public int IgetField(int id);

    public boolean IisItemValidForSlot(int index, ItemStack stack);

    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te);

    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te);

}
