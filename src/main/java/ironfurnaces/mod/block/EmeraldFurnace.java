package ironfurnaces.mod.block;

import ironfurnaces.mod.IronFurnaces;
import ironfurnaces.mod.gui.GuiHandler;
import ironfurnaces.mod.init.IronFurnacesBlocks;
import ironfurnaces.mod.tileentity.TileEntityCopperFurnace;
import ironfurnaces.mod.tileentity.TileEntityEmeraldFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EmeraldFurnace extends IronFurnaceBase {

	public EmeraldFurnace(String unlocName, String regName) {
		super(unlocName, regName, "Emerald Furnace");
	}

	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(IronFurnacesBlocks.emerald_furnace);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(IronFurnacesBlocks.emerald_furnace);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEmeraldFurnace();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityEmeraldFurnace) {
				player.openGui(IronFurnaces.instance, GuiHandler.GuiEmeraldFurnace, world, pos.getX(), pos.getY(),
						pos.getZ());
			}
			return true;
		}
	}

}
