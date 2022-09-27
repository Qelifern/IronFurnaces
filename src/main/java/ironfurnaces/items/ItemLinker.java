package ironfurnaces.items;

import ironfurnaces.Config;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import ironfurnaces.util.StringHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemLinker extends Item {
    public ItemLinker(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {

        if (BlockIronFurnaceScreenBase.isShiftKeyDown())
        {
            tooltip.add(Component.literal("Sneak & right-click on a furnace to save it").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Sneak & right-click on your Rainbow Furnace to add the saved furnace and/or show how many furnaces are still missing").withStyle(ChatFormatting.GRAY));
        }
        else
        {
            tooltip.add(StringHelper.getShiftInfoText());
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (!world.isClientSide)
        {
            if (context.getPlayer().isCrouching()) {
                if (world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase)
                {
                    BlockIronFurnaceTileBase tile = (BlockIronFurnaceTileBase) world.getBlockEntity(pos);
                    if (tile != null) {
                        if (!(tile instanceof BlockMillionFurnaceTile)) {
                            stack.getOrCreateTag().putInt("X", pos.getX());
                            stack.getOrCreateTag().putInt("Y", pos.getY());
                            stack.getOrCreateTag().putInt("Z", pos.getZ());
                            context.getPlayer().sendSystemMessage(Component.literal("Saved: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()));
                        } else {
                            List<BlockIronFurnaceTileBase> list = ((BlockMillionFurnaceTile) tile).furnaces;

                            int str = Config.millionFurnacePower.get() - list.size();
                            context.getPlayer().sendSystemMessage(Component.literal("Missing: "  + str));




                            if (stack.hasTag()) {

                                int x = stack.getTag().getInt("X");
                                int y = stack.getTag().getInt("Y");
                                int z = stack.getTag().getInt("Z");
                                if (world.getBlockEntity(new BlockPos(x, y, z)) instanceof BlockIronFurnaceTileBase)
                                {
                                    BlockIronFurnaceTileBase toAdd = ((BlockIronFurnaceTileBase) world.getBlockEntity(new BlockPos(x, y, z)));
                                    if (toAdd != null)
                                    {
                                        boolean flag = true;


                                        for (int i = 0; i < list.size(); i++)
                                        {
                                            if (toAdd.getBlockState().getBlock() == list.get(i).getBlockState().getBlock()) {
                                                flag = false;
                                            }
                                        }


                                        if (flag)
                                        {
                                            context.getPlayer().sendSystemMessage(Component.literal("Added: " + toAdd.getName().getString()));
                                            ((BlockMillionFurnaceTile) tile).furnaces.add(toAdd);
                                            toAdd.linkedPos = tile.getBlockPos();
                                        }
                                    }
                                }


                            }
                        }

                    }
                }

            }
        }

        return super.useOn(context);
    }
}
