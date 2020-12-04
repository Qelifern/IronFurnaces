package ironfurnaces.items;

import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends Item {

    private int[] available;
    boolean one = false;

    public ItemUpgrade(int[] available) {
        super(new Properties().group(ModSetup.ITEM_GROUP));
        this.available = available;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Sneak & right-click to upgrade"));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        boolean playSound = false;
        if (!world.isRemote) {
            if (ModList.get().isLoaded("fastfurnace"))
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
                BlockState next = this.getNextTierBlock(te, available).getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2) ? this.getNextTierBlock(te, available).getStateForPlacement(ctx2) : world.getBlockState(pos);
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

    public static Block getNextTierBlock(TileEntity te, int[] available) {
        Block block = te.getBlockState().getBlock();
        if (block == Blocks.FURNACE && available[0] == 1) {
            return Registration.IRON_FURNACE.get();
        } else
        if (block == Registration.IRON_FURNACE.get() && available[1] == 1) {
            return Registration.GOLD_FURNACE.get();
        } else
        if (block == Registration.GOLD_FURNACE.get() && available[2] == 1) {
            return Registration.DIAMOND_FURNACE.get();
        } else
        if (block == Registration.DIAMOND_FURNACE.get() && available[3] == 1) {
            return Registration.EMERALD_FURNACE.get();
        } else
        if (block == Registration.EMERALD_FURNACE.get() && available[4] == 1) {
            return Registration.OBSIDIAN_FURNACE.get();
        }
        if (block == Registration.DIAMOND_FURNACE.get() && available[5] == 1) {
            return Registration.CRYSTAL_FURNACE.get();
        }
        if (block == Registration.CRYSTAL_FURNACE.get() && available[6] == 1) {
            return Registration.OBSIDIAN_FURNACE.get();
        }
        if (block == Registration.OBSIDIAN_FURNACE.get() && available[7] == 1) {
            return Registration.NETHERITE_FURNACE.get();
        }
        return Blocks.AIR;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!(stack.getItem() instanceof ItemUpgradeIron))
        {
            return false;
        }
        if (!ModList.get().isLoaded("fastfurnace"))
        {
            return false;
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
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.IRON_INGOT, 8)));

                one = false;
            }
        }
        return false;
    }
}
