package ironfurnaces.items;

import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.BlockMillionFurnaceTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ItemLinker extends Item {
    public ItemLinker(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        ItemStack stack = context.getItem();
        if (!world.isRemote) {
            if (context.getPlayer().isSneaking()) {
                BlockIronFurnaceTileBase tile = (BlockIronFurnaceTileBase) world.getTileEntity(pos);
                if (tile != null) {
                    if (!(tile instanceof BlockMillionFurnaceTile)) {
                        stack.getOrCreateTag().putInt("X", pos.getX());
                        stack.getOrCreateTag().putInt("Y", pos.getY());
                        stack.getOrCreateTag().putInt("Z", pos.getZ());
                        context.getPlayer().sendMessage(new StringTextComponent("Saved: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()), context.getPlayer().getUniqueID());
                    } else {
                        if (stack.hasTag()) {

                            int x = stack.getTag().getInt("X");
                            int y = stack.getTag().getInt("Y");
                            int z = stack.getTag().getInt("Z");
                            BlockIronFurnaceTileBase toAdd = ((BlockIronFurnaceTileBase) world.getTileEntity(new BlockPos(x, y, z)));
                            boolean flag = false;
                            List<BlockIronFurnaceTileBase> list = ((BlockMillionFurnaceTile) tile).furnaces;
                            if (list.size() <= 0)
                            {
                                flag = true;
                            }
                            else
                            {
                                for (int i = 0; i < list.size(); i++)
                                {
                                    if (toAdd.getBlockState().getBlock() != list.get(i).getBlockState().getBlock()) {
                                        flag = true;
                                    }
                                }
                            }

                            if (flag)
                            {
                                context.getPlayer().sendMessage(new StringTextComponent("Added: " + toAdd.getName().getString()), context.getPlayer().getUniqueID());
                                ((BlockMillionFurnaceTile) tile).furnaces.add(toAdd);
                            }
                        }
                    }
                }
            }
        }

        return super.onItemUse(context);
    }
}
