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
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (!world.isClientSide) {
            if (context.getPlayer().isCrouching()) {
                BlockIronFurnaceTileBase tile = (BlockIronFurnaceTileBase) world.getBlockEntity(pos);
                if (tile != null) {
                    if (!(tile instanceof BlockMillionFurnaceTile)) {
                        stack.getOrCreateTag().putInt("X", pos.getX());
                        stack.getOrCreateTag().putInt("Y", pos.getY());
                        stack.getOrCreateTag().putInt("Z", pos.getZ());
                        context.getPlayer().sendMessage(new StringTextComponent("Saved: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()), context.getPlayer().getUUID());
                    } else {
                        if (stack.hasTag()) {

                            int x = stack.getTag().getInt("X");
                            int y = stack.getTag().getInt("Y");
                            int z = stack.getTag().getInt("Z");
                            BlockIronFurnaceTileBase toAdd = ((BlockIronFurnaceTileBase) world.getBlockEntity(new BlockPos(x, y, z)));
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
                                context.getPlayer().sendMessage(new StringTextComponent("Added: " + toAdd.getName().getString()), context.getPlayer().getUUID());
                                ((BlockMillionFurnaceTile) tile).furnaces.add(toAdd);
                            }
                        }
                    }
                }
            }
        }

        return super.useOn(context);
    }
}
