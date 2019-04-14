package ironfurnaces.mod.gui;

import ironfurnaces.mod.container.ContainerIronFurance;
import ironfurnaces.mod.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GuiIronFurnace = 0;
    public static final int GuiGoldFurnace = 1;
    public static final int GuiDiamondFurnace = 2;
    public static final int GuiEmeraldFurnace = 3;
    public static final int GuiObsidianFurnace = 4;
    public static final int GuiCopperFurnace = 5;
    public static final int GuiSilverFurnace = 6;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Iron Furnace");
            case 1:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Gold Furnace");
            case 2:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Diamond Furnace");
            case 3:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Emerald Furnace");
            case 4:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Obsidian Furnace");
            case 5:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Copper Furnace");
            case 6:
                return new GuiIronFurance(player.inventory, (TileEntityIronFurnaceBase) world.getTileEntity(new BlockPos(x, y, z)), "Silver Furnace");
        }
        return null;
    }

}
