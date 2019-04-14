package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ironfurnaces.Main;
import ironfurnaces.blocks.BlockIronFurnaceBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class TileEntityIronFurnaceBase extends TileEntityInventory implements ITickable, IRecipeHolder, IRecipeHelperPopulator {

    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{2, 1};
    private static final int[] SLOTS_SIDES = new int[]{1};
    private int timer;
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime = this.getCookTime();
    private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

    public TileEntityIronFurnaceBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, 3);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setInt("BurnTime", this.furnaceBurnTime);
        compound.setInt("CookTime", this.cookTime);
        compound.setInt("CookTimeTotal", this.totalCookTime);
        compound.setShort("RecipesUsedSize", (short) this.recipeUseCounts.size());
        int i = 0;

        for (Map.Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
            compound.setString("RecipeLocation" + i, entry.getKey().toString());
            compound.setInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        this.furnaceBurnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.totalCookTime = compound.getInt("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.inventory.get(1));
        this.timer = 0;
        int i = compound.getShort("RecipesUsedSize");
        for (int j = 0; j < i; ++j) {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
            int k = compound.getInt("RecipeAmount" + j);
            this.recipeUseCounts.put(resourcelocation, k);
        }
    }

    public Map<ResourceLocation, Integer> getRecipeUseCounts() {
        return this.recipeUseCounts;
    }

    protected int getCookTime() {
        return 200;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void tick() {
        Main.LOGGER.fatal("CookTime: " + this.totalCookTime + " : " + this.getCookTime());
        timer++;
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {
            if (this.totalCookTime != this.getCookTime()) {
                this.totalCookTime = this.getCookTime();
            }
            ItemStack itemstack = this.inventory.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !this.inventory.get(0).isEmpty()) {
                IRecipe irecipe = this.world.getRecipeManager().getRecipe(this, this.world, net.minecraftforge.common.crafting.VanillaRecipeTypes.SMELTING);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.furnaceBurnTime = getItemBurnTime(itemstack) * this.getCookTime() / 200;
                    this.currentItemBurnTime = this.furnaceBurnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem()) {
                            this.inventory.set(1, itemstack.getContainerItem());
                        } else if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                Item item1 = item.getContainerItem();
                                this.inventory.set(1, item1 == null ? ItemStack.EMPTY : new ItemStack(item1));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime >= this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime();
                        this.smeltItem(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }
        }
        if (timer % 30 == 0) {
            if (world.getBlockState(pos).getBlock() instanceof BlockIronFurnaceBase) {
                if (this.isBurning() != world.getBlockState(pos).get(BlockIronFurnaceBase.LIT)) {
                    IBlockState oldState = world.getBlockState(pos);
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockIronFurnaceBase.LIT, this.isBurning()), 3);
                    world.notifyBlockUpdate(pos, oldState, world.getBlockState(pos), 3);
                    world.markBlockRangeForRenderUpdate(pos, pos);
                    flag1 = true;
                }
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private boolean canSmelt(@Nullable IRecipe recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.inventory.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() < itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void smeltItem(@Nullable IRecipe recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.inventory.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.inventory.get(2);
            if (itemstack2.isEmpty()) {
                this.inventory.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.canUseRecipe(this.world, (EntityPlayerMP) null, recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't fuel
     */
    private static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime();
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? TileEntityFurnace.getBurnTimes().getOrDefault(item, 0) : ret);
        }
    }

    @Override
    public int[] IgetSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN) {
            return SLOTS_BOTTOM;
        } else {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int IgetFieldCount() {
        return 4;
    }

    @Override
    public void IsetField(int id, int value) {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int IgetField(int id) {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.inventory.get(1);
            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public void onCrafting(EntityPlayer player) {
        if (!this.world.getGameRules().getBoolean("doLimitedCrafting")) {
            List<IRecipe> list = Lists.newArrayList();

            for (ResourceLocation resourcelocation : this.recipeUseCounts.keySet()) {
                IRecipe irecipe = player.world.getRecipeManager().getRecipe(resourcelocation);
                if (irecipe != null) {
                    list.add(irecipe);
                }
            }

            player.unlockRecipes(list);
        }

        this.recipeUseCounts.clear();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack itemstack : this.inventory) {
            helper.accountStack(itemstack);
        }

    }

    @Override
    public void setRecipeUsed(IRecipe recipe) {
        if (this.recipeUseCounts.containsKey(recipe.getId())) {
            this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
        } else {
            this.recipeUseCounts.put(recipe.getId(), 1);
        }

    }

    @Override
    public void openInventory(EntityPlayer player) {
        IBlockState oldState = world.getBlockState(pos);
        world.setBlockState(pos, world.getBlockState(pos).with(BlockIronFurnaceBase.LIT, this.isBurning()), 3);
        world.notifyBlockUpdate(pos, oldState, world.getBlockState(pos), 3);
        world.markBlockRangeForRenderUpdate(pos, pos);
        this.markDirty();
    }

    @Nullable
    public IRecipe getRecipeUsed() {
        return null;
    }

    public void setCustomName(@Nullable ITextComponent name) {
        this.name = name;
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable EnumFacing facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP)
                return handlers[0].cast();
            else if (facing == EnumFacing.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }
}
