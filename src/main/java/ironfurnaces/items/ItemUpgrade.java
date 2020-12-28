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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.setFormatting((TextFormatting.GRAY))));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        boolean playSound = false;
        ctx.getPlayer().sendMessage(new StringTextComponent(ctx.getPlayer().getUniqueID().toString()), ctx.getPlayer().getUniqueID());
        if (!world.isRemote) {
            if ((ctx.getItem().getItem() instanceof ItemUpgradeIron || ctx.getItem().getItem() instanceof ItemUpgradeCopper) && ModList.get().isLoaded("fastfurnace"))
            {
                ctx.getPlayer().sendMessage(new StringTextComponent("FastFurnace Mod is loaded, will not upgrade, drop the upgrade on the floor together with one cobblestone to get your materials back."), ctx.getPlayer().getUniqueID());
                return super.onItemUse(ctx);
            }
            TileEntity te = world.getTileEntity(pos);
            BlockItemUseContext ctx2 = new BlockItemUseContext(ctx);
            if (te instanceof FurnaceTileEntity || te instanceof BlockIronFurnaceTileBase) {
                int cooktime = 0;
                int currentItemBurnTime = 0;
                int furnaceBurnTime = 0;
                if (te instanceof BlockIronFurnaceTileBase) {
                    furnaceBurnTime = ((BlockIronFurnaceTileBase) te).fields.get(0);
                    currentItemBurnTime = ((BlockIronFurnaceTileBase) te).fields.get(1);
                    cooktime = ((BlockIronFurnaceTileBase) te).fields.get(2);
                }
                if (te.getBlockState().getBlock() != from)
                {
                    return ActionResultType.PASS;
                }
                BlockState next = to.getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2) ? to.getStateForPlacement(ctx2) : world.getBlockState(pos);
                if (next == world.getBlockState(pos)) {
                    return ActionResultType.PASS;
                }
                ItemStack input = ((IInventory) te).getStackInSlot(0).copy();
                ItemStack fuel  = ((IInventory) te).getStackInSlot(1).copy();
                ItemStack output  = ((IInventory) te).getStackInSlot(2).copy();
                ItemStack augment  = ItemStack.EMPTY;
                if (te instanceof BlockIronFurnaceTileBase) {
                    augment = ((IInventory) te).getStackInSlot(3).copy();
                }
                world.removeTileEntity(te.getPos());
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                world.setBlockState(pos, next, 3);
                TileEntity newTe = world.getTileEntity(pos);
                ((IInventory)newTe).setInventorySlotContents(0, input);
                ((IInventory)newTe).setInventorySlotContents(1, fuel);
                ((IInventory)newTe).setInventorySlotContents(2, output);
                if (newTe instanceof BlockIronFurnaceTileBase) {
                    ((IInventory)newTe).setInventorySlotContents(3, augment);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(0, furnaceBurnTime);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(1, currentItemBurnTime);
                    ((BlockIronFurnaceTileBase)newTe).fields.set(2, cooktime);
                }
                if (!ctx.getPlayer().isCreative()) {
                    ctx.getItem().shrink(1);
                }
            }
        }
        return super.onItemUse(ctx);
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
        World world = entity.getEntityWorld();
        if (!world.isRemote) {
            List<ItemEntity> list = world.getEntitiesWithinAABB(EntityType.ITEM,
                    new AxisAlignedBB(entity.getPosX() - 0.5, entity.getPosY() - 0.5, entity.getPosZ() - 0.5, entity.getPosX() + 0.5, entity.getPosY() + 0.5, entity.getPosZ() + 0.5),
                    new UpgradeItems());

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getItem().equals(new ItemStack(Blocks.COBBLESTONE, 1), false)) {
                    one = true;
                }
            }
            if (one) {

                BlockPos pos = new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ());
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).remove();
                }
                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
                lightningboltentity.moveForced(pos.getX(), pos.getY(), pos.getZ());
                lightningboltentity.setEffectOnly(true);
                world.addEntity(lightningboltentity);
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), materials));

                one = false;
            }
        }
        return false;
    }
}
