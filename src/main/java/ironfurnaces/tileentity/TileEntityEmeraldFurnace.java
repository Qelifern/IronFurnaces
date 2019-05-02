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

public class TileEntityEmeraldFurnace extends TileEntityIronFurnaceBase {
    public TileEntityEmeraldFurnace() {
        super(ModBlocks.EMERALD_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return Config.SERVER.emeraldFurnaceSpeed.get();
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:emerald_furnace";
    }

    @Override
    public String IgetName() {
        return "container.emerald_furnace";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnace(playerInventory,  (TileEntityEmeraldFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
