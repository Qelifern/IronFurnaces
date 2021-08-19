package ironfurnaces.items;

import ironfurnaces.IronFurnaces;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import ironfurnaces.util.ItemTagsIronFurnaces;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends Item {

    private Block from;
    private Block to;
    boolean one = false;

    public ItemUpgrade(Properties properties, Block from, Block to) {
        super(properties);
        this.from = from;
        this.to = to;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.applyFormat((TextFormatting.GRAY))));
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!world.isClientSide) {
            if ((ctx.getItemInHand().getItem() instanceof ItemUpgradeIron || ctx.getItemInHand().getItem() instanceof ItemUpgradeCopper) && ModList.get().isLoaded("fastfurnace"))
            {
                ctx.getPlayer().sendMessage(new StringTextComponent("FastFurnace Mod is loaded, will not upgrade, drop the upgrade on the floor together with one cobblestone to get your materials back."), ctx.getPlayer().getUUID());
                return super.useOn(ctx);
            }
            TileEntity te = world.getBlockEntity(pos);
            BlockItemUseContext ctx2 = new BlockItemUseContext(ctx);
            if (te instanceof FurnaceTileEntity || te instanceof BlockIronFurnaceTileBase) {
                int cooktime = 0;
                int currentItemBurnTime = 0;
                int furnaceBurnTime = 0;
                int show = 0;
                int[] settings = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                if (te instanceof BlockIronFurnaceTileBase) {
                    furnaceBurnTime = ((BlockIronFurnaceTileBase) te).fields.get(0);
                    currentItemBurnTime = ((BlockIronFurnaceTileBase) te).fields.get(1);
                    cooktime = ((BlockIronFurnaceTileBase) te).fields.get(2);
                    show = ((BlockIronFurnaceTileBase) te).fields.get(4);
                    for (int i = 0; i < ((BlockIronFurnaceTileBase) te).furnaceSettings.size(); i++)
                    {
                        settings[i] = ((BlockIronFurnaceTileBase) te).furnaceSettings.get(i);
                    }

                }
                if (te.getBlockState().getBlock() != from)
                {
                    return ActionResultType.PASS;
                }
                BlockState next = to.getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2) ? to.getStateForPlacement(ctx2) : world.getBlockState(pos);
                if (next == world.getBlockState(pos)) {
                    return ActionResultType.PASS;
                }
                ItemStack input = ((IInventory) te).getItem(0).copy();
                ItemStack fuel  = ((IInventory) te).getItem(1).copy();
                ItemStack output  = ((IInventory) te).getItem(2).copy();
                ItemStack augment  = ItemStack.EMPTY;
                if (te instanceof BlockIronFurnaceTileBase) {
                    augment = ((IInventory) te).getItem(3).copy();
                }
                world.removeBlockEntity(te.getBlockPos());
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(pos, next, 3);
                TileEntity newTe = world.getBlockEntity(pos);
                ((IInventory)newTe).setItem(0, input);
                ((IInventory)newTe).setItem(1, fuel);
                ((IInventory)newTe).setItem(2, output);
                if (newTe instanceof BlockIronFurnaceTileBase) {
                    ((IInventory)newTe).setItem(3, augment);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(0, furnaceBurnTime);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(1, currentItemBurnTime);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(2, cooktime);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(4, show);
                    for (int i = 0; i < ((BlockIronFurnaceTileBase)newTe).furnaceSettings.size(); i++)
                    {
                        ((BlockIronFurnaceTileBase)newTe).furnaceSettings.set(i, settings[i]);
                    }
                }
                world.markAndNotifyBlock(pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos),3,  3);
                if (!ctx.getPlayer().isCreative()) {
                    ctx.getItemInHand().shrink(1);
                }
            }
        }
        return super.useOn(ctx);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!ModList.get().isLoaded("fastfurnace"))
        {
            return false;
        }
        ItemStack materials = ItemStack.EMPTY;
        if (!(stack.getItem() instanceof ItemUpgradeIron) && !(stack.getItem() instanceof ItemUpgradeCopper))
        {
            return false;
        }
        else
        {
            if (stack.getItem() instanceof ItemUpgradeIron)
            {
                materials = new ItemStack(Items.IRON_INGOT, 8);
            }
            else
            {
                if (ItemTagsIronFurnaces.getOreDict("ingots/copper") != null)
                {
                    ItemStack copper = new ItemStack(ItemTagsIronFurnaces.getOreDict("ingots/copper"), 8);
                    if (copper.isEmpty()) {
                        return false;
                    }
                    materials = copper;
                }
                else
                {
                    return false;
                }
            }
        }
        World world = entity.level;
        if (!world.isClientSide) {
            List<ItemEntity> list = world.getEntities(EntityType.ITEM,
                    new AxisAlignedBB(entity.position().x - 0.5, entity.position().y - 0.5, entity.position().z - 0.5, entity.position().x + 0.5, entity.position().y + 0.5, entity.position().z + 0.5),
                    new UpgradeItems());

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getItem().equals(new ItemStack(Blocks.COBBLESTONE, 1), false)) {
                    one = true;
                }
            }
            if (one) {

                BlockPos pos = new BlockPos(entity.position().x, entity.position().y, entity.position().z);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).remove();
                }
                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
                lightningboltentity.moveTo(pos.getX(), pos.getY(), pos.getZ());
                lightningboltentity.setVisualOnly(true);
                world.addFreshEntity(lightningboltentity);
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), materials));

                one = false;
            }
        }
        return false;
    }
}
