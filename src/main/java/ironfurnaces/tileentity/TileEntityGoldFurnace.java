package ironfurnaces.tileentity;

import ironfurnaces.container.ContainerIronFurnace;
import ironfurnaces.gui.GuiIronFurnaceBase;
import ironfurnaces.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGoldFurnace extends TileEntityIronFurnaceBase {
    public TileEntityGoldFurnace() {
        super(ModBlocks.GOLD_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return 120;
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:gold_furnace";
    }

    @Override
    public String IgetName() {
        return "container.gold_furnace";
    }

    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnaceBase(playerInventory,  (TileEntityGoldFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
