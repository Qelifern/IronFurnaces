package ironfurnaces.mod.item;

import ironfurnaces.mod.IronFurnaces;
import ironfurnaces.mod.init.IronFurnacesBlocks;
import ironfurnaces.mod.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends Item {

    private int[] available;

    public ItemUpgrade(int[] available) {
        this.available = available;
        this.setCreativeTab(IronFurnaces.TAB_IRONFURNACES);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Sneak & right-click to upgrade");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityFurnace || te instanceof TileEntityIronFurnaceBase) {
                int cooktime;
                int currentItemBurnTime;
                int furnaceBurnTime;
                if (te instanceof TileEntityFurnace) {
                    furnaceBurnTime = ((TileEntityFurnace)te).getField(0);
                    currentItemBurnTime = ((TileEntityFurnace)te).getField(1);
                    cooktime = ((TileEntityFurnace)te).getField(2);
                } else {
                    furnaceBurnTime = ((TileEntityIronFurnaceBase)te).getField(0);
                    currentItemBurnTime = ((TileEntityIronFurnaceBase)te).getField(1);
                    cooktime = ((TileEntityIronFurnaceBase)te).getField(2);
                }
                IBlockState next = this.getNextTierBlock(te, available).getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, 0, player, hand) != null ? this.getNextTierBlock(te, available).getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, 0, player, hand) : null;
                if (next == null) {
                    return EnumActionResult.PASS;
                }
                ItemStack input = ((IInventory) te).getStackInSlot(0).copy();
                ItemStack fuel  = ((IInventory) te).getStackInSlot(1).copy();
                ItemStack output  = ((IInventory) te).getStackInSlot(2).copy();
                world.removeTileEntity(te.getPos());
                world.setBlockState(pos, next, 3);
                TileEntity newTe = world.getTileEntity(pos);
                ((IInventory)newTe).setInventorySlotContents(0, input);
                ((IInventory)newTe).setInventorySlotContents(1, fuel);
                ((IInventory)newTe).setInventorySlotContents(2, output);
                if (newTe instanceof TileEntityFurnace) {
                    ((TileEntityFurnace)newTe).setField(0, furnaceBurnTime);
                    ((TileEntityFurnace)newTe).setField(1, currentItemBurnTime);
                    ((TileEntityFurnace)newTe).setField(2, cooktime);
                } else {
                    ((TileEntityIronFurnaceBase)newTe).setField(0, furnaceBurnTime);
                    ((TileEntityIronFurnaceBase)newTe).setField(1, currentItemBurnTime);
                    ((TileEntityIronFurnaceBase)newTe).setField(2, cooktime);
                }
                if (!player.isCreative()) {
                    player.getHeldItem(hand).shrink(1);
                }
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    public static Block getNextTierBlock(TileEntity te, int[] available) {
        Block block = te.getBlockType();
        if (block == Blocks.FURNACE && available[0] == 1) {
            return IronFurnacesBlocks.iron_furnace;
        } else
        if (block == IronFurnacesBlocks.iron_furnace && available[1] == 1) {
            return IronFurnacesBlocks.gold_furnace;
        } else
        if (block == IronFurnacesBlocks.gold_furnace && available[2] == 1) {
            return IronFurnacesBlocks.diamond_furnace;
        } else
        if (block == IronFurnacesBlocks.diamond_furnace && available[3] == 1) {
            return IronFurnacesBlocks.emerald_furnace;
        } else
        if (block == IronFurnacesBlocks.emerald_furnace && available[4] == 1) {
            return IronFurnacesBlocks.obsidian_furnace;
        }
        return null;
    }
}
