package ironfurnaces.tileentity;

import ironfurnaces.container.ContainerIronFurnace;
import ironfurnaces.gui.GuiIronFurnaceBase;
import ironfurnaces.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class TileEntityObsidianFurnace extends TileEntityIronFurnaceBase {
    public TileEntityObsidianFurnace() {
        super(ModBlocks.OBSIDIAN_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return 40;
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:obsidian_furnace";
    }

    @Override
    public String IgetName() {
        return "container.obsidian_furnace";
    }

    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnaceBase(playerInventory,  (TileEntityObsidianFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
