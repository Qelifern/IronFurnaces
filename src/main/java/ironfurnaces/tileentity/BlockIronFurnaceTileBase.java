package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import ironfurnaces.Config;
import ironfurnaces.blocks.BlockIronFurnaceBase;
import ironfurnaces.init.Registration;
import ironfurnaces.items.*;
import ironfurnaces.util.DirectionUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class BlockIronFurnaceTileBase extends TileEntityInventory implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {
    public final int[] provides = new int[Direction.values().length];
    private final int[] lastProvides = new int[this.provides.length];

    public static final int INPUT = 0;
    public static final int FUEL = 1;
    public static final int OUTPUT = 2;

    protected AbstractCookingRecipe curRecipe;

    private Random rand = new Random();

    public int show_inventory_settings;
    private int jovial;
    protected int timer;
    private int currentAugment; // 0 == none 1 == Blasting 2 == Smoking 3 == Speed 4 == Fuel
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    public int cookTime;
    private int totalCookTime = this.getCookTime(true);
    private Item cachedItem;
    private Item cachedAugment;
    private int recipesUsed;
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

    public IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public FurnaceSettings furnaceSettings;


    public BlockIronFurnaceTileBase(TileEntityType<?> tileentitytypeIn) {
        super(tileentitytypeIn, 4);
        this.recipeType = IRecipeType.SMELTING;
        furnaceSettings = new FurnaceSettings() {
            @Override
            public void onChanged() {
                setChanged();
            }
        };

    }

    protected int getCookTime(boolean set) {

            ItemStack stack = this.getItem(3);
            if (!set)
            {
                if (!stack.isEmpty()) {
                    if (stack.getItem() instanceof ItemAugmentSpeed) {
                        return Math.max(1, (getSpeed() / 2));
                    }
                    if (stack.getItem() instanceof ItemAugmentFuel) {
                        return Math.max(1, (int) (Math.ceil(getSpeed() * 1.25)));
                    }

                }
            }
            if (cachedItem != this.getItem(0).getItem() || cachedAugment != stack.getItem()) {
                if (this.getItem(0).getItem() == Items.AIR) {
                    return totalCookTime;
                }
                int speed = getSpeed();

                if (!stack.isEmpty()) {
                    if (stack.getItem() instanceof ItemAugmentSpeed) {
                        speed = Math.max(1, (speed / 2));
                    }
                    if (stack.getItem() instanceof ItemAugmentFuel) {
                        speed = Math.max(1, (int) (Math.ceil(speed * 1.25)));
                    }

                }
                if (set)
                {
                    cachedItem = this.getItem(0).getItem();
                    cachedAugment = this.getItem(3).getItem();
                }

                return speed;


            }


        return totalCookTime;

    }

    protected int getSpeed() {
        int i = getCookTimeConfig().get();
        if (this.recipeType != null) {
            if (this.recipeType == IRecipeType.SMELTING) {
                int j = this.level.getRecipeManager().getRecipeFor((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.level).map(AbstractCookingRecipe::getCookingTime).orElse((i));

                //System.out.println("Grabbing cooktime from recipe: " + j);
                if (j < i) {
                    int k = j - (200 - i);
                    //System.out.println("Actual cooktime: " + k);
                    return Math.max(k, 1);
                } else {
                    //System.out.println("Actual cooktime: " + i);
                    return i;
                }
            } else {
                int j = this.level.getRecipeManager().getRecipeFor((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.level).map(AbstractCookingRecipe::getCookingTime).orElse((i));

                //System.out.println("Grabbing cooktime from recipe: " + j);
                if (j < i) {
                    int k = j - (100 - (i / 2));
                    //System.out.println("Actual cooktime: " + k);
                    return Math.max(k, 1);
                } else {
                    //System.out.println("Actual cooktime: " + i / 2);
                    return i / 2;
                }
            }

        }
        return 0;
    }

    protected ForgeConfigSpec.IntValue getCookTimeConfig() {
        return null;
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
                case 4:
                    return BlockIronFurnaceTileBase.this.show_inventory_settings;
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
                    break;
                case 4:
                    BlockIronFurnaceTileBase.this.show_inventory_settings = value;
                    break;
            }

        }

        @Override
        public int getCount() {
            return 5;
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

    public void forceUpdateAllStates() {
        BlockState state = level.getBlockState(worldPosition);
        if (state.getValue(BlockStateProperties.LIT) != this.isBurning()) {
            level.setBlock(worldPosition, state.setValue(BlockStateProperties.LIT, this.isBurning()), 3);
        }
        if (state.getValue(BlockIronFurnaceBase.TYPE) != this.getStateType()) {
            level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.TYPE, this.getStateType()), 3);
        }
        if (state.getValue(BlockIronFurnaceBase.JOVIAL) != this.jovial) {
            level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.JOVIAL, this.jovial), 3);
        }
    }

    @Override
    public void tick() {
        if (furnaceSettings.size() <= 0) {
            furnaceSettings = new FurnaceSettings() {
                @Override
                public void onChanged() {
                    setChanged();
                }
            };
        }
        boolean wasBurning = this.isBurning();
        boolean flag1 = false;
        boolean flag2 = false;
        if (currentAugment != getAugment(this.getItem(3))) {
            this.currentAugment = getAugment(this.getItem(3));
            this.furnaceBurnTime = 0;
        }
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }
        if (!this.level.isClientSide) {

            timer++;
            if (this.cookTime <= 0) {
                autoIO();
                flag1 = true;
            }
/**
            if (this.totalCookTime != this.getCookTime()) {
                this.totalCookTime = this.getCookTime();
            }**/
            int mode = this.getRedstoneSetting();
            if (mode != 0) {
                if (mode == 2) {
                    int i = 0;
                    for (Direction side : Direction.values()) {
                        if (level.getSignal(worldPosition.offset(side.getNormal()), side) > 0) {
                            i++;
                        }
                    }
                    if (i != 0) {
                        this.cookTime = 0;
                        this.furnaceBurnTime = 0;
                        forceUpdateAllStates();
                        return;
                    }
                }
                if (mode == 1) {
                    boolean flag = false;
                    for (Direction side : Direction.values()) {

                        if (level.getSignal(worldPosition.offset(side.getNormal()), side) > 0) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        this.cookTime = 0;
                        this.furnaceBurnTime = 0;
                        forceUpdateAllStates();
                        return;
                    }
                }
                for (int i = 0; i < Direction.values().length; i++)
                    this.provides[i] = getBlockState().getDirectSignal(this.level, worldPosition, Direction.from2DDataValue(i));

            } else {
                for (int i = 0; i < Direction.values().length; i++)
                    this.provides[i] = 0;
            }
            if (this.doesNeedUpdateSend()) {
                this.onUpdateSent();
            }
            ItemStack stack = this.getItem(3);
            if (stack.getItem() instanceof ItemAugmentBlasting) {
                if (this.recipeType != IRecipeType.BLASTING) {
                    this.recipeType = IRecipeType.BLASTING;
                }
            } else if (stack.getItem() instanceof ItemAugmentSmoking) {
                if (this.recipeType != IRecipeType.SMOKING) {
                    this.recipeType = IRecipeType.SMOKING;
                }
            } else {
                if (this.recipeType != IRecipeType.SMELTING) {
                    this.recipeType = IRecipeType.SMELTING;
                }
            }
            ItemStack itemstack = this.inventory.get(FUEL);
            if (this.isBurning() || !itemstack.isEmpty() && !this.inventory.get(INPUT).isEmpty()) {
                AbstractCookingRecipe irecipe = level.getRecipeManager().getRecipeFor((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.level).orElse(null);
                boolean valid = this.canSmelt(irecipe);
                if (!this.isBurning() && valid) {
                    if (itemstack.getItem() instanceof ItemHeater) {
                        if (itemstack.hasTag()) {
                            int x = itemstack.getTag().getInt("X");
                            int y = itemstack.getTag().getInt("Y");
                            int z = itemstack.getTag().getInt("Z");
                            TileEntity te = level.getBlockEntity(new BlockPos(x, y, z));
                            if (te instanceof BlockWirelessEnergyHeaterTile) {
                                int energy = ((BlockWirelessEnergyHeaterTile) te).getEnergy();
                                if (energy >= 2000) {
                                    if (!this.getItem(3).isEmpty() && this.getItem(3).getItem() instanceof ItemAugmentFuel) {
                                        this.furnaceBurnTime = 400 * this.getCookTime(false) / 200;
                                    } else if (!this.getItem(3).isEmpty() && this.getItem(3).getItem() instanceof ItemAugmentSpeed) {
                                        if (energy >= 4000) {
                                            ((BlockWirelessEnergyHeaterTile) te).removeEnergy(2000);
                                            this.furnaceBurnTime = 200 * this.getCookTime(false) / 200;
                                        }
                                    } else {
                                        this.furnaceBurnTime = 200 * this.getCookTime(false) / 200;
                                    }
                                    if (this.furnaceBurnTime > 0)
                                        ((BlockWirelessEnergyHeaterTile) te).removeEnergy(2000);

                                    this.recipesUsed = this.furnaceBurnTime;
                                }
                            }
                        }
                    } else {
                        if (!this.getItem(3).isEmpty() && this.getItem(3).getItem() instanceof ItemAugmentFuel) {
                            this.furnaceBurnTime = 2 * (getBurnTime(itemstack)) * this.getCookTime(false) / 200;
                        } else if (!this.getItem(3).isEmpty() && this.getItem(3).getItem() instanceof ItemAugmentSpeed) {
                            this.furnaceBurnTime = (getBurnTime(itemstack) / 2) * this.getCookTime(false) / 200;
                        } else {
                            this.furnaceBurnTime = getBurnTime(itemstack) * this.getCookTime(false) / 200;
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
                        this.totalCookTime = this.getCookTime(true);
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
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(BlockStateProperties.LIT, this.isBurning()), 3);
            }
            if (timer % 24 == 0) {
                BlockState state = level.getBlockState(worldPosition);
                if (state.getValue(BlockIronFurnaceBase.TYPE) != this.getStateType()) {
                    level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.TYPE, this.getStateType()), 3);
                }
                if (state.getValue(BlockIronFurnaceBase.JOVIAL) != this.jovial) {
                    level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.JOVIAL, this.jovial), 3);
                }
                //System.out.println(totalCookTime);


            }
        }

        if (flag1) {
            this.setChanged();
        }

    }

    private void autoIO() {
        for (Direction dir : Direction.values()) {
            TileEntity tile = level.getBlockEntity(worldPosition.offset(dir.getNormal()));
            if (tile == null) {
                continue;
            }
            if (this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3 || this.furnaceSettings.get(dir.ordinal()) == 4) {
                if (tile != null) {
                    IItemHandler other = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).map(other1 -> other1).orElse(null);

                    if (other == null) {
                        continue;
                    }
                    if (other != null) {
                        if (this.getAutoInput() != 0 || this.getAutoOutput() != 0) {
                            if (this.getAutoInput() == 1) {
                                if (this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 3) {
                                    if (this.getItem(INPUT).getCount() >= this.getItem(INPUT).getMaxStackSize()) {
                                        continue;
                                    }
                                    for (int i = 0; i < other.getSlots(); i++) {
                                        if (other.getStackInSlot(i).isEmpty()) {
                                            continue;
                                        }
                                        ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true);
                                        if (hasRecipe(stack) && getItem(INPUT).isEmpty() || ItemHandlerHelper.canItemStacksStack(getItem(INPUT), stack)) {
                                            insertItemInternal(INPUT, other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - this.getItem(INPUT).getCount(), false), false);
                                        }
                                    }
                                }
                                if (this.furnaceSettings.get(dir.ordinal()) == 4) {
                                    if (this.getItem(FUEL).getCount() >= this.getItem(FUEL).getMaxStackSize()) {
                                        continue;
                                    }
                                    for (int i = 0; i < other.getSlots(); i++) {
                                        if (other.getStackInSlot(i).isEmpty()) {
                                            continue;
                                        }
                                        ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true);
                                        if (isItemFuel(stack) && getItem(FUEL).isEmpty() || ItemHandlerHelper.canItemStacksStack(getItem(FUEL), stack)) {
                                            insertItemInternal(FUEL, other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - this.getItem(FUEL).getCount(), false), false);
                                        }
                                    }
                                }
                            }
                            if (this.getAutoOutput() == 1) {

                                if (this.furnaceSettings.get(dir.ordinal()) == 4) {
                                    if (this.getItem(FUEL).isEmpty()) {
                                        continue;
                                    }
                                    ItemStack stack = extractItemInternal(FUEL, 1, true);
                                    if (stack.getItem() != Items.BUCKET) {
                                        continue;
                                    }
                                    for (int i = 0; i < other.getSlots(); i++) {
                                        if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) {
                                            other.insertItem(i, extractItemInternal(FUEL, stack.getCount(), false), false);
                                        }
                                    }
                                }

                                if (this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) {
                                    if (this.getItem(OUTPUT).isEmpty()) {
                                        continue;
                                    }
                                    if (tile.getBlockState().getBlock().getRegistryName().toString().contains("storagedrawers:")) {
                                        continue;
                                    }
                                    for (int i = 0; i < other.getSlots(); i++) {
                                        ItemStack stack = extractItemInternal(OUTPUT, this.getItem(OUTPUT).getMaxStackSize() - other.getStackInSlot(i).getCount(), true);
                                        if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) {
                                            other.insertItem(i, extractItemInternal(OUTPUT, stack.getCount(), false), false);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean hasRecipe(ItemStack stack) {
        ItemStack augment = this.inventory.get(3);
        if (augment.getItem() instanceof ItemAugmentBlasting) {
            if (this.recipeType != IRecipeType.BLASTING) {
                this.recipeType = IRecipeType.BLASTING;
            }
        } else if (augment.getItem() instanceof ItemAugmentSmoking) {
            if (this.recipeType != IRecipeType.SMOKING) {
                this.recipeType = IRecipeType.SMOKING;
            }
        } else {
            if (this.recipeType != IRecipeType.SMELTING) {
                this.recipeType = IRecipeType.SMELTING;
            }
        }
        return this.level.getRecipeManager().getRecipeFor((IRecipeType) this.recipeType, new Inventory(stack), this.level).isPresent();
    }

    @Nonnull
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!canPlaceItemThroughFace(slot, stack, null))
            return stack;

        ItemStack existing = this.inventory.get(slot);

        int limit = stack.getMaxStackSize();

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.inventory.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            this.setChanged();
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    private ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existing = this.getItem(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.setItem(slot, ItemStack.EMPTY);
                this.setChanged();
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.setItem(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                this.setChanged();
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    //CLIENT SYNC
    public int getSettingBottom() {
        return this.furnaceSettings.get(0);
    }

    public int getSettingTop() {
        return this.furnaceSettings.get(1);
    }

    public int getSettingFront() {
        int i = DirectionUtil.getId(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING));
        return this.furnaceSettings.get(i);
    }

    public int getSettingBack() {
        int i = DirectionUtil.getId(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
        return this.furnaceSettings.get(i);
    }

    public int getSettingLeft() {
        Direction facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.EAST));
        } else if (facing == Direction.WEST) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.NORTH));
        } else if (facing == Direction.SOUTH) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.WEST));
        } else {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.SOUTH));
        }
    }

    public int getSettingRight() {
        Direction facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.WEST));
        } else if (facing == Direction.WEST) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.SOUTH));
        } else if (facing == Direction.SOUTH) {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.EAST));
        } else {
            return this.furnaceSettings.get(DirectionUtil.getId(Direction.NORTH));
        }
    }

    public int getIndexFront() {
        int i = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).ordinal();
        return i;
    }

    public int getIndexBack() {
        int i = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().ordinal();
        return i;
    }

    public int getIndexLeft() {
        Direction facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return Direction.EAST.ordinal();
        } else if (facing == Direction.WEST) {
            return Direction.NORTH.ordinal();
        } else if (facing == Direction.SOUTH) {
            return Direction.WEST.ordinal();
        } else {
            return Direction.SOUTH.ordinal();
        }
    }

    public int getIndexRight() {
        Direction facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return Direction.WEST.ordinal();
        } else if (facing == Direction.WEST) {
            return Direction.SOUTH.ordinal();
        } else if (facing == Direction.SOUTH) {
            return Direction.EAST.ordinal();
        } else {
            return Direction.NORTH.ordinal();
        }
    }

    public int getAutoInput() {
        return this.furnaceSettings.get(6);
    }

    public int getAutoOutput() {
        return this.furnaceSettings.get(7);
    }

    public int getRedstoneSetting() {
        return this.furnaceSettings.get(8);
    }

    public int getRedstoneComSub() {
        return this.furnaceSettings.get(9);
    }


    private int getStateType() {
        if (this.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
            return 1;
        } else if (this.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
            return 2;
        } else {
            return 0;
        }
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack recipeOutput = recipe.getResultItem();
            if (!recipeOutput.isEmpty()) {
                ItemStack output = this.inventory.get(OUTPUT);
                if (output.isEmpty()) return true;
                else if (!output.sameItem(recipeOutput)) return false;
                else return output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize();
            }
        }
        return false;
    }

    protected void smeltItem(@Nullable IRecipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.inventory.get(INPUT);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.inventory.get(OUTPUT);
            if (itemstack2.isEmpty()) {
                this.inventory.set(OUTPUT, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }
            this.checkXP(recipe);
            if (!this.level.isClientSide) {
                this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(FUEL).isEmpty() && this.inventory.get(FUEL).getItem() == Items.BUCKET) {
                this.inventory.set(FUEL, new ItemStack(Items.WATER_BUCKET));
            }
            if (ModList.get().isLoaded("pmmo")) {
                //FurnaceHandler.handleSmelted(itemstack, itemstack2, level, worldPosition, 0);
                //FurnaceHandler.handleSmelted(itemstack, itemstack2, level, worldPosition, 1);
            }
            itemstack.shrink(1);
        }
    }


    @Override
    public void load(BlockState state, CompoundNBT tag) {
        ItemStackHelper.loadAllItems(tag, this.inventory);
        this.furnaceBurnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.totalCookTime = tag.getInt("CookTimeTotal");
        this.timer = 0;
        this.currentAugment = tag.getInt("Augment");
        this.jovial = tag.getInt("Jovial");
        this.recipesUsed = this.getBurnTime(this.inventory.get(1));
        CompoundNBT compoundnbt = tag.getCompound("RecipesUsed");

        for (String s : compoundnbt.getAllKeys()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
        this.show_inventory_settings = tag.getInt("ShowInvSettings");
        this.furnaceSettings.read(tag);
        /**
         CompoundNBT energyTag = tag.getCompound("energy");
         energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
         **/

        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        ItemStackHelper.saveAllItems(tag, this.inventory);
        tag.putInt("BurnTime", this.furnaceBurnTime);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.totalCookTime);
        tag.putInt("Augment", this.currentAugment);
        tag.putInt("Jovial", this.jovial);

        tag.putInt("ShowInvSettings", this.show_inventory_settings);
        this.furnaceSettings.write(tag);
        /**
         energy.ifPresent(h -> {
         CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
         tag.put("energy", compound);
         });
         **/

        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            compoundnbt.putInt(recipeId.toString(), craftedAmount);
        });
        tag.put("RecipesUsed", compoundnbt);

        return tag;
    }

    protected static int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime();
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? AbstractFurnaceTileEntity.getFuel().getOrDefault(item, 0) : ret);
        }
    }


    public static boolean isItemFuel(ItemStack stack) {
        return getBurnTime(stack) > 0 || stack.getItem() instanceof ItemHeater;
    }

    public static boolean isItemAugment(ItemStack stack) {
        return stack.getItem() instanceof ItemAugment;
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] invHandlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

    @Nonnull
    @Override
    public <
            T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {

        if (!this.isRemoved() && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.DOWN)
                return invHandlers[0].cast();
            else if (facing == Direction.UP)
                return invHandlers[1].cast();
            else if (facing == Direction.NORTH)
                return invHandlers[2].cast();
            else if (facing == Direction.SOUTH)
                return invHandlers[3].cast();
            else if (facing == Direction.WEST)
                return invHandlers[4].cast();
            else
                return invHandlers[5].cast();
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
        if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 0) {
            return new int[]{};
        } else if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 1) {
            return new int[]{0, 1};
        } else if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 2) {
            return new int[]{2};
        } else if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 3) {
            return new int[]{0, 1, 2};
        } else if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 4) {
            return new int[]{1};
        }
        return new int[]{};
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 0) {
            return false;
        } else if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 1) {
            return false;
        } else if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 2) {
            return index == 2;
        } else if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 3) {
            return index == 2;
        } else if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 4 && stack.getItem() != Items.BUCKET) {
            return false;
        } else if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 4 && stack.getItem() == Items.BUCKET) {
            return true;
        }
        return false;
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == OUTPUT || index == 3) {
            return false;
        }
        if (index == INPUT) {
            return this.level.getRecipeManager().getRecipeFor((IRecipeType) this.recipeType, new Inventory(stack), this.level).isPresent();
        }
        if (index == FUEL) {
            ItemStack itemstack = this.inventory.get(FUEL);
            return getBurnTime(stack) > 0 || (stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET) || stack.getItem() instanceof ItemHeater;
        }
        return false;
    }

    public void setJovial(int value) {
        this.jovial = value;
    }

    public void checkXP(@Nullable IRecipe<?> recipe)
    {
        if (!level.isClientSide) {
            boolean flag2 = false;
            if (this.recipes.size() > Config.furnaceXPDropValue.get()) {
                this.grantStoredRecipeExperience(level, new Vector3d(worldPosition.getX() + rand.nextInt(2) - 1, worldPosition.getY(), worldPosition.getZ() + rand.nextInt(2) - 1));
                this.recipes.clear();
            } else {
                for (Object2IntMap.Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
                    if (level.getRecipeManager().byKey(entry.getKey()).isPresent()) {
                        if (entry.getIntValue() > Config.furnaceXPDropValue2.get()) {
                            if (!flag2) {
                                this.grantStoredRecipeExperience(level, new Vector3d(worldPosition.getX() + rand.nextInt(2) - 1, worldPosition.getY(), worldPosition.getZ() + rand.nextInt(2) - 1));
                            }
                            flag2 = true;
                        }
                    }

                }
                if (flag2) {
                    this.recipes.clear();
                }
            }
        }
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

    public void unlockRecipes(PlayerEntity player) {
        List<IRecipe<?>> list = this.grantStoredRecipeExperience(player.level, player.position());
        player.awardRecipes(list);
        this.recipes.clear();
    }

    public List<IRecipe<?>> grantStoredRecipeExperience(World level, Vector3d worldPosition) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((h) -> {
                list.add(h);
                splitAndSpawnExperience(level, worldPosition, entry.getIntValue(), ((AbstractCookingRecipe) h).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(World level, Vector3d worldPosition, int craftedAmount, float experience) {
        int i = MathHelper.floor((float) craftedAmount * experience);
        float f = MathHelper.frac((float) craftedAmount * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrbEntity(level, worldPosition.x, worldPosition.y, worldPosition.z, j));
        }

    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack itemstack : this.inventory) {
            helper.accountStack(itemstack);
        }

    }

    protected boolean doesNeedUpdateSend() {
        return !Arrays.equals(this.provides, this.lastProvides);
    }

    public void onUpdateSent() {
        System.arraycopy(this.provides, 0, this.lastProvides, 0, this.provides.length);
        this.level.updateNeighborsAt(this.worldPosition, getBlockState().getBlock());
    }


    public void placeConfig() {

        if (this.furnaceSettings != null) {
            this.furnaceSettings.set(0, 2);
            this.furnaceSettings.set(1, 1);
            for (Direction dir : Direction.values()) {
                if (dir != Direction.DOWN && dir != Direction.UP) {
                    this.furnaceSettings.set(dir.ordinal(), 4);
                }
            }
            level.markAndNotifyBlock(worldPosition, level.getChunkAt(worldPosition), level.getBlockState(worldPosition).getBlock().defaultBlockState(), level.getBlockState(worldPosition), 3, 3);
        }

    }
}
