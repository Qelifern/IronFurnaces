package ironfurnaces.tileentity;

import ironfurnaces.config.ObsidianFurnaceConfig;
import ironfurnaces.container.ContainerIronFurnace;
import ironfurnaces.gui.GuiIronFurnaceBase;
import ironfurnaces.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityObsidianFurnace extends TileEntityIronFurnaceBase {
    public TileEntityObsidianFurnace() {
        super(ModBlocks.OBSIDIAN_FURNACE);
    }

    @Override
    protected int getCookTime() {
        return ObsidianFurnaceConfig.SPEED.get();
    }

    @Override
    public String IgetGuiID() {
        return "ironfurnaces:obsidian_furnace";
    }

    @Override
    public String IgetName() {
        return "container.obsidian_furnace";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public GuiContainer IcreateGui(InventoryPlayer playerInventory, TileEntity te) {
        return new GuiIronFurnaceBase(playerInventory,  (TileEntityObsidianFurnace) te);
    }

    @Override
    public Container IcreateContainer(InventoryPlayer playerInventory, TileEntity te) {
        return new ContainerIronFurnace(playerInventory, (TileEntityIronFurnaceBase) te);
    }
}
