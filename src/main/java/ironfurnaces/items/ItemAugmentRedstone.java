package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAugmentRedstone extends ItemAugment {

    public ItemAugmentRedstone(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.hasTag())
        {
            int mode = stack.getTag().getInt("Mode");
            if (mode == 0)
            {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_redstone_control").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            }
            else if (mode == 1)
            {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_redstone_control_inverse").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            }
            else if (mode == 2)
            {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_redstone_comparator").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            }
            else if (mode == 3)
            {
                tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_redstone_comparator_subtract").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
            }
        }
        tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".augment_redstone_right_click").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));

    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        stack.getOrCreateTag().putInt("Mode", 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote)
        {
            int mode = playerIn.getHeldItem(handIn).getOrCreateTag().getInt("Mode");
            mode++;
            playerIn.getHeldItem(handIn).getOrCreateTag().putInt("Mode", mode);
            if (mode >= 4)
            {
                playerIn.getHeldItem(handIn).getOrCreateTag().putInt("Mode", 0);
                mode = 0;
            }
            if (mode == 0) {
                sendPlayerMessage(playerIn, "augment_redstone_msg_mode0");
            }
            else if (mode == 1) {
                sendPlayerMessage(playerIn, "augment_redstone_msg_mode1");
            }
            else if (mode == 2) {
                sendPlayerMessage(playerIn, "augment_redstone_msg_mode2");
            }
            else if (mode == 3) {
                sendPlayerMessage(playerIn, "augment_redstone_msg_mode3");
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void sendPlayerMessage(PlayerEntity player, String messageTranslationKey)
    {
        player.sendStatusMessage(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + "." + messageTranslationKey), true);
    }
}
