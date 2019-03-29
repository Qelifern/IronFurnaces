package ironfurnaces.items;

import ironfurnaces.init.ModBlocks;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends Item {

    private int[] available;

    public ItemUpgrade(Properties properties, int[] available) {
        super(properties);
        this.available = available;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString("Sneak & right-click to upgrade"));
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        boolean playSound = false;
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            BlockItemUseContext ctx2 = new BlockItemUseContext(ctx);
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
                IBlockState next = this.getNextTierBlock(te, available).getStateForPlacement(ctx2) != null ? this.getNextTierBlock(te, available).getStateForPlacement(ctx2) : null;
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
                if (!ctx.getPlayer().isCreative()) {
                    ctx.getItem().shrink(1);
                }
            }
        }
        return super.onItemUse(ctx);
    }

    public static Block getNextTierBlock(TileEntity te, int[] available) {
        Block block = te.getBlockState().getBlock();
        if (block == Blocks.FURNACE && available[0] == 1) {
            return ModBlocks.iron_furnace;
        } else
        if (block == ModBlocks.iron_furnace && available[1] == 1) {
            return ModBlocks.gold_furnace;
        } else
        if (block == ModBlocks.gold_furnace && available[2] == 1) {
            return ModBlocks.diamond_furnace;
        } else
        if (block == ModBlocks.diamond_furnace && available[3] == 1) {
            return ModBlocks.obsidian_furnace;
        }
        return null;
    }
}
