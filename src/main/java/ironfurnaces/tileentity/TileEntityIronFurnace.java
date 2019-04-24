package ironfurnaces.tileentity;

import ironfurnaces.config.Config;
import ironfurnaces.container.ContainerIronFurnace;
import ironfurnaces.gui.GuiIronFurnace;
import ironfurnaces.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityIronFurnace extends TileEntityIronFurnaceBase {
    public TileEntityIronFurnace() {
        super(ModBlocks.IRON_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.ironFurnaceSpeed.get();
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:iron_furnace";
    }

    @Override
    public String IgetName() {
        return "container.iron_furnace";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnace(playerInventory,  (TileEntityIronFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
