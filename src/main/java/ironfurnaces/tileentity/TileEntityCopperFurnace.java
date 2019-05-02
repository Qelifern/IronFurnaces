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

public class TileEntityCopperFurnace extends TileEntityIronFurnaceBase {
    public TileEntityCopperFurnace() {
        super(ModBlocks.COPPER_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.copperFurnaceSpeed.get();
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:copper_furnace";
    }

    @Override
    public String IgetName() {
        return "container.copper_furnace";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnace(playerInventory,  (TileEntityCopperFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
