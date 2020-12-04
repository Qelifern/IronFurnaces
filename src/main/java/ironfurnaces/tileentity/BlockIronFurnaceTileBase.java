package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import ironfurnaces.blocks.BlockIronFurnaceBase;
import ironfurnaces.init.Registration;
import ironfurnaces.items.*;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockIronFurnaceTileBase extends TileEntityInventory implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};

    public static final int INPUT = 0;
    public static final int FUEL = 1;
    public static final int OUTPUT = 2;

    protected AbstractCookingRecipe curRecipe;
    protected ItemStack failedMatch = ItemStack.EMPTY;

    private int jovial = 0;
    private int timer;
    private int currentAugment = 0; // 0 == none 1 == Blasting 2 == Smoking 3 == Speed 4 == Fuel
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    private int cookTime;
    private int totalCookTime = this.getCookTime();
    private int recipesUsed;
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

    public IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public BlockIronFurnaceTileBase(TileEntityType<?> tileentitytypeIn) {
        super(tileentitytypeIn, 4);
        this.recipeType = IRecipeType.SMELTING;
    }

    protected int getCookTime() {
        ItemStack stack = this.getStackInSlot(3);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemAugmentSpeed) {
                return getCookTimeConfig() > 1 ? getCookTimeConfig() / 2 : getCookTimeConfig();
            }
            if (stack.getItem() instanceof ItemAugmentFuel)
            {
                return (int) (getCookTimeConfig() * 1.25);
            }
        }
        return getCookTimeConfig();

    }

    protected int getCookTimeConfig() {
        return 200;
    }

    public final IIntArray fields = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return BlockIronFurnaceTileBase.this.furnaceBurnTime;
                case 1:
                    return BlockIronFurnaceTileBase.this.recipesUsed;
                case 2:
                    return BlockIronFurnaceTileBase.this.cookTime;
                case 3:
                    return BlockIronFurnaceTileBase.this.totalCookTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    BlockIronFurnaceTileBase.this.furnaceBurnTime = value;
                    break;
                case 1:
                    BlockIronFurnaceTileBase.this.recipesUsed = value;
                    break;
                case 2:
                    BlockIronFurnaceTileBase.this.cookTime = value;
                    break;
                case 3:
                    BlockIronFurnaceTileBase.this.totalCookTime = value;
            }

        }

        public int size() {
            return 4;
        }
    };

    private int getAugment(ItemStack stack) {
        if (stack.getItem() instanceof ItemAugmentBlasting) {
            return 1;
        } else if (stack.getItem() instanceof ItemAugmentSmoking) {
            return 2;
        } else if (stack.getItem() instanceof ItemAugmentSpeed) {
            return 3;
        } else if (stack.getItem() instanceof ItemAugmentFuel) {
            return 4;
        }
        return 0;
    }

    @Override
    public void tick() {
        boolean wasBurning = this.isBurning();
        boolean flag1 = false;
        if (currentAugment != getAugment(this.getStackInSlot(3))) {
            this.currentAugment = getAugment(this.getStackInSlot(3));
            this.furnaceBurnTime = 0;
        }
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {
            timer++;
            if (this.totalCookTime != this.getCookTime()) {
                this.totalCookTime = this.getCookTime();
            }
            if (!this.getStackInSlot(3).isEmpty()) {
                if (this.getStackInSlot(3).getItem() instanceof ItemAugmentBlasting) {
                    if (this.recipeType != IRecipeType.BLASTING) {
                        this.recipeType = IRecipeType.BLASTING;
                    }
                } else if (this.getStackInSlot(3).getItem() instanceof ItemAugmentSmoking) {
                    if (this.recipeType != IRecipeType.SMOKING) {
                        this.recipeType = IRecipeType.SMOKING;
                    }
                }
            } else {
                if (this.recipeType != IRecipeType.SMELTING) {
                    this.recipeType = IRecipeType.SMELTING;
                }
            }
            ItemStack itemstack = this.inventory.get(FUEL);
            if (this.isBurning() || !itemstack.isEmpty() && !this.inventory.get(INPUT).isEmpty()) {
                AbstractCookingRecipe irecipe = getRecipe();
                boolean valid = this.canSmelt(irecipe);
                if (!this.isBurning() && valid) {
                    if (itemstack.getItem() instanceof ItemHeater) {
                        if (itemstack.hasTag()) {
                            int x = itemstack.getTag().getInt("X");
                            int y = itemstack.getTag().getInt("Y");
                            int z = itemstack.getTag().getInt("Z");
                            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                            if (te instanceof BlockWirelessEnergyHeaterTile) {
                                int energy = ((BlockWirelessEnergyHeaterTile) te).getEnergy();
                                if (energy >= 16000) {
                                    ((BlockWirelessEnergyHeaterTile) te).removeEnergy(16000);
                                    if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentFuel) {
                                        this.furnaceBurnTime = 3200 * this.getCookTime() / 200;
                                    } else if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentSpeed) {
                                        this.furnaceBurnTime = 800 * this.getCookTime() / 200;
                                    } else {
                                        this.furnaceBurnTime = 1600 * this.getCookTime() / 200;
                                    }
                                    this.recipesUsed = this.furnaceBurnTime;
                                }
                            }
                        }
                    } else {
                        if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentFuel) {
                            this.furnaceBurnTime = 2 * (getBurnTime(itemstack)) * this.getCookTime() / 200;
                        } else if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentSpeed) {
                            this.furnaceBurnTime = (getBurnTime(itemstack) / 2) * this.getCookTime() / 200;
                        } else {
                            this.furnaceBurnTime = getBurnTime(itemstack) * this.getCookTime() / 200;
                        }
                        this.recipesUsed = this.furnaceBurnTime;
                    }
                    if (this.isBurning()) {
                        flag1 = true;
                        if (!(itemstack.getItem() instanceof ItemHeater)) {
                            if (itemstack.hasContainerItem()) this.inventory.set(FUEL, itemstack.getContainerItem());
                            else if (!itemstack.isEmpty()) {
                                itemstack.shrink(1);
                                if (itemstack.isEmpty()) {
                                    this.inventory.set(FUEL, itemstack.getContainerItem());
                                }
                            }
                        }
                    }
                }

                if (this.isBurning() && valid) {
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
            if (wasBurning != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockStateProperties.LIT, this.isBurning()), 3);
            }
            if (timer % 24 == 0) {
                BlockState state = world.getBlockState(pos);
                if (state.get(BlockIronFurnaceBase.TYPE) != this.getStateType())
                {
                    world.setBlockState(pos, state.with(BlockIronFurnaceBase.TYPE, this.getStateType()), 3);
                }
                if (state.get(BlockIronFurnaceBase.JOVIAL) != this.jovial)
                {
                    world.setBlockState(pos, state.with(BlockIronFurnaceBase.JOVIAL, this.jovial), 3);
                }
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    @SuppressWarnings("unchecked")
    protected AbstractCookingRecipe getRecipe() {
        ItemStack input = this.getStackInSlot(INPUT);
        if (input.isEmpty() || input == failedMatch) return null;
        if (curRecipe != null && curRecipe.matches(this, world)) return curRecipe;
        else {
            AbstractCookingRecipe rec = world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).orElse(null);
            if (rec == null) failedMatch = input;
            else failedMatch = ItemStack.EMPTY;
            return curRecipe = rec;
        }
    }

    private int getStateType()
    {
        if (this.getStackInSlot(3).getItem() == Registration.SMOKING_AUGMENT.get())
        {
            return 1;
        }
        else if (this.getStackInSlot(3).getItem() == Registration.BLASTING_AUGMENT.get())
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    private boolean canSmelt(@Nullable IRecipe<?> recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack recipeOutput = recipe.getRecipeOutput();
            if (!recipeOutput.isEmpty()) {
                ItemStack output = this.inventory.get(OUTPUT);
                if (output.isEmpty()) return true;
                else if (!output.isItemEqual(recipeOutput)) return false;
                else return output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize();
            }
        }
        return false;
    }

    private void smeltItem(@Nullable IRecipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.inventory.get(INPUT);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.inventory.get(OUTPUT);
            if (itemstack2.isEmpty()) {
                this.inventory.set(OUTPUT, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(FUEL).isEmpty() && this.inventory.get(FUEL).getItem() == Items.BUCKET) {
                this.inventory.set(FUEL, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        ItemStackHelper.loadAllItems(tag, this.inventory);
        this.furnaceBurnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.totalCookTime = tag.getInt("CookTimeTotal");
        this.timer = 0;
        this.currentAugment = tag.getInt("Augment");
        this.jovial = tag.getInt("Jovial");
        this.recipesUsed = this.getBurnTime(this.inventory.get(1));
        CompoundNBT compoundnbt = tag.getCompound("RecipesUsed");

        for(String s : compoundnbt.keySet()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
        /**
         CompoundNBT energyTag = tag.getCompound("energy");
         energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
         **/

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        ItemStackHelper.saveAllItems(tag, this.inventory);
        tag.putInt("BurnTime", this.furnaceBurnTime);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.totalCookTime);
        tag.putInt("Augment", this.currentAugment);
        tag.putInt("Jovial", this.jovial);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            compoundnbt.putInt(recipeId.toString(), craftedAmount);
        });
        tag.put("RecipesUsed", compoundnbt);
        /**
         energy.ifPresent(h -> {
         CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
         tag.put("energy", compound);
         });
         **/

        return super.write(tag);
    }

    protected static int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime();
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(item, 0) : ret);
        }
    }


    public static boolean isItemFuel(ItemStack stack) {
        return getBurnTime(stack) > 0 || stack.getItem() instanceof ItemHeater;
    }

    public static boolean isItemAugment(ItemStack stack) {
        return stack.getItem() instanceof ItemAugment;
    }


    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Nonnull
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        /**
         if (cap == CapabilityEnergy.ENERGY) {
         return energy.cast();
         }
         **/
        return super.getCapability(capability, facing);
    }


    @Override
    public int[] IgetSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.inventory.get(1);
            return FurnaceTileEntity.isFuel(stack) || FurnaceFuelSlot.isBucket(stack) && itemstack.getItem() != Items.BUCKET || itemstack.getItem() instanceof ItemHeater;
        }
    }

    public void setJovial(int value)
    {
        this.jovial = value;
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipes.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void onCrafting(PlayerEntity player) {
    }

    public void unlockRecipes(PlayerEntity player) {
        List<IRecipe<?>> list = this.grantStoredRecipeExperience(player.world, player.getPositionVec());
        player.unlockRecipes(list);
        this.recipes.clear();
    }

    public List<IRecipe<?>> grantStoredRecipeExperience(World world, Vector3d pos) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
            world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(World world, Vector3d pos, int craftedAmount, float experience) {
        int i = MathHelper.floor((float)craftedAmount * experience);
        float f = MathHelper.frac((float)craftedAmount * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        while(i > 0) {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            world.addEntity(new ExperienceOrbEntity(world, pos.x, pos.y, pos.z, j));
        }

    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for(ItemStack itemstack : this.inventory) {
            helper.accountStack(itemstack);
        }

    }
}
