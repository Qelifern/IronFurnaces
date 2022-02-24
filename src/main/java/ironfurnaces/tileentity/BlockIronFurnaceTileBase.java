package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import com.mojang.math.Vector3d;
import harmonised.pmmo.events.FurnaceHandler;
import ironfurnaces.Config;
import ironfurnaces.blocks.BlockIronFurnaceBase;
import ironfurnaces.init.Registration;
import ironfurnaces.items.*;
import ironfurnaces.util.DirectionUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class BlockIronFurnaceTileBase extends TileEntityInventory implements RecipeHolder, StackedContentsCompatible {

    public static final EntityDataAccessor<Float> SHOW_CONFIG = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);

    public final int[] provides = new int[Direction.values().length];
    protected final int[] lastProvides = new int[this.provides.length];

    public static final int INPUT = 0;
    public static final int FUEL = 1;
    public static final int OUTPUT = 2;

    protected Random rand = new Random();

    protected int jovial;
    protected int timer;
    protected int currentAugment; // 0 == none 1 == Blasting 2 == Smoking 3 == Speed 4 == Fuel
    /**
     * The number of ticks that the furnace will keep burning
     */
    public int furnaceBurnTime;
    public int cookTime;
    public int totalCookTime = this.getCookTime();
    public int recipesUsed;
    protected final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

    public RecipeType<? extends AbstractCookingRecipe> recipeType;

    public FurnaceSettings furnaceSettings;

    protected LRUCache<Item, Optional<AbstractCookingRecipe>> cache = LRUCache.newInstance(Config.cache_capacity.get());
    protected LRUCache<Item, Optional<AbstractCookingRecipe>> blasting_cache = LRUCache.newInstance(Config.cache_capacity.get());
    protected LRUCache<Item, Optional<AbstractCookingRecipe>> smoking_cache = LRUCache.newInstance(Config.cache_capacity.get());
    public BlockPos linkedPos = new BlockPos(0, 0, 0);


    public BlockIronFurnaceTileBase(BlockEntityType<?> tileentitytypeIn, BlockPos pos, BlockState state) {
        super(tileentitytypeIn, pos, state, 4);
        this.recipeType = RecipeType.SMELTING;
        furnaceSettings = new FurnaceSettings() {
            @Override
            public void onChanged() {
                setChanged();
            }
        };


    }



    public boolean hasRecipe(ItemStack stack) {
        return grabRecipe(stack).isPresent();
    }

    protected Optional<AbstractCookingRecipe> getRecipe(Item  item) {
        return (item instanceof AirItem)
                ? Optional.empty()
                : Optional.ofNullable(this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>) this.recipeType, this, this.level).orElse(null));
    }

    protected Optional<AbstractCookingRecipe> grabRecipe() {
        Item item = getItem(INPUT).getItem();
        if (item instanceof AirItem)
        {
            return Optional.empty();
        }
        Optional<AbstractCookingRecipe> recipe = getCache().get(item);
        if (recipe == null) {
            recipe = this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>) this.recipeType, this, this.level);
            getCache().put(item, recipe);
        }
        return recipe;
    }

    protected Optional<AbstractCookingRecipe> grabRecipe(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof AirItem)
        {
            return Optional.empty();
        }
        Optional<AbstractCookingRecipe> recipe = getCache().get(item);
        if (recipe == null) {
            recipe = this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>) this.recipeType, new SimpleContainer(stack), this.level);
            getCache().put(item, recipe);
        }
        return recipe;
    }

    protected LRUCache<Item, Optional<AbstractCookingRecipe>> getCache() {
        ItemStack stack = this.getItem(3);
        if (stack.getItem() instanceof ItemAugmentBlasting) {
            if (this.recipeType != RecipeType.BLASTING) {
                this.recipeType = RecipeType.BLASTING;
            }
        }
        if (stack.getItem() instanceof ItemAugmentSmoking) {
            if (this.recipeType != RecipeType.SMOKING) {
                this.recipeType = RecipeType.SMOKING;
            }
        }
        if (!(stack.getItem() instanceof ItemAugmentSmoking) && !(stack.getItem() instanceof ItemAugmentBlasting))
        {
            if (this.recipeType != RecipeType.SMELTING) {
                this.recipeType = RecipeType.SMELTING;
            }
        }
        if (this.recipeType == RecipeType.BLASTING) {
            return blasting_cache;
        }
        if (this.recipeType == RecipeType.SMOKING) {
            return smoking_cache;
        }
        return cache;
    }

    protected int getCookTime() {

        ItemStack stack = this.getItem(3);

        if (this.getItem(INPUT).getItem() == Items.AIR) {
            return totalCookTime;
        }
        int speed = getSpeed();
        if (speed == -1) {
            return -1;
        }
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemAugmentSpeed || stack.getItem() instanceof ItemAugmentBlasting || stack.getItem() instanceof ItemAugmentSmoking) {
                speed = Math.max(1, (speed / 2));
            }
            if (stack.getItem() instanceof ItemAugmentFuel) {
                speed = Math.max(1, (int) (Math.ceil(speed * 1.25)));
            }
        }


        return Math.max(1, speed);

    }

    protected int getSpeed() {
        int i = getCookTimeConfig().get();
        int j = getCache().computeIfAbsent(getItem(INPUT).getItem(), this::getRecipe).map(AbstractCookingRecipe::getCookingTime).orElse(0);
        if (j == 0) {
            Optional<AbstractCookingRecipe> recipe = grabRecipe();
            j = !recipe.isPresent() ? -1 : recipe.orElse(null).getCookingTime();
            getCache().put(this.getItem(INPUT).getItem(), recipe);

            if (j == -1) {
                return -1;
            }
        }
        if (j < i) {
            int k = j - (200 - i);
            return k;
        } else {
            return i;
        }
    }

    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return null;
    }

    protected int getAugment(ItemStack stack) {
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


    public static void tick(Level level, BlockPos worldPosition, BlockState blockState, BlockIronFurnaceTileBase e) {
        if (e.furnaceSettings.size() <= 0) {
            e.furnaceSettings = new FurnaceSettings() {
                @Override
                public void onChanged() {
                    e.setChanged();
                }
            };
        }
        boolean wasBurning = e.isBurning();
        boolean flag1 = false;
        boolean flag2 = false;
        if (e.currentAugment != e.getAugment(e.getItem(3))) {
            e.currentAugment = e.getAugment(e.getItem(3));
            e.furnaceBurnTime = 0;
        }
        if (e.isBurning()) {
            --e.furnaceBurnTime;
        }
        ItemStack stack = e.getItem(3);
        if (stack.getItem() instanceof ItemAugmentBlasting) {
            if (e.recipeType != RecipeType.BLASTING) {
                e.recipeType = RecipeType.BLASTING;
            }
        }
        if (stack.getItem() instanceof ItemAugmentSmoking) {
            if (e.recipeType != RecipeType.SMOKING) {
                e.recipeType = RecipeType.SMOKING;
            }
        }
        if (!(stack.getItem() instanceof ItemAugmentSmoking) && !(stack.getItem() instanceof ItemAugmentBlasting))
        {
            if (e.recipeType != RecipeType.SMELTING) {
                e.recipeType = RecipeType.SMELTING;
            }
        }
        if (!e.level.isClientSide) {

            e.timer++;


            if (e.totalCookTime != e.getCookTime()) {
                e.totalCookTime = e.getCookTime();
            }
            int mode = e.getRedstoneSetting();
            if (mode != 0) {
                if (mode == 2) {
                    int i = 0;
                    for (Direction side : Direction.values()) {
                        if (level.getSignal(worldPosition.offset(side.getNormal()), side) > 0) {
                            i++;
                        }
                    }
                    if (i != 0) {
                        e.cookTime = 0;
                        e.furnaceBurnTime = 0;
                        e.forceUpdateAllStates();
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
                        e.cookTime = 0;
                        e.furnaceBurnTime = 0;
                        e.forceUpdateAllStates();
                        return;
                    }
                }
                for (int i = 0; i < Direction.values().length; i++)
                    e.provides[i] = e.getBlockState().getDirectSignal(e.level, worldPosition, DirectionUtil.fromId(i));

            } else {
                for (int i = 0; i < Direction.values().length; i++)
                    e.provides[i] = 0;
            }
            if (e.doesNeedUpdateSend()) {
                e.onUpdateSent();
            }
            ItemStack itemstack = e.getItem(FUEL);
            if (e.isBurning() || !itemstack.isEmpty() && !e.getItem(INPUT).isEmpty()) {
                Optional<AbstractCookingRecipe> irecipe = Optional.empty();
                if (!e.getItem(INPUT).isEmpty()) {
                    irecipe = e.grabRecipe();
                }

                boolean valid = e.canSmelt(irecipe.orElse(null));
                if (!e.isBurning() && valid) {
                    if (itemstack.getItem() instanceof ItemHeater) {
                        if (itemstack.hasTag()) {
                            int x = itemstack.getTag().getInt("X");
                            int y = itemstack.getTag().getInt("Y");
                            int z = itemstack.getTag().getInt("Z");
                            BlockEntity te = level.getBlockEntity(new BlockPos(x, y, z));
                            if (te instanceof BlockWirelessEnergyHeaterTile) {
                                int energy = ((BlockWirelessEnergyHeaterTile) te).getEnergy();
                                if (energy >= 2000) {
                                    if (!e.getItem(3).isEmpty() && e.getItem(3).getItem() instanceof ItemAugmentFuel) {
                                        e.furnaceBurnTime = 400 * e.getCookTime() / 200;
                                    } else if (!e.getItem(3).isEmpty() && e.getItem(3).getItem() instanceof ItemAugmentSpeed) {
                                        if (energy >= 4000) {
                                            ((BlockWirelessEnergyHeaterTile) te).removeEnergy(2000);
                                            e.furnaceBurnTime = 200 * e.getCookTime() / 200;
                                        }
                                    } else {
                                        e.furnaceBurnTime = 200 * e.getCookTime() / 200;
                                    }
                                    if (e.furnaceBurnTime > 0)
                                        ((BlockWirelessEnergyHeaterTile) te).removeEnergy(2000);

                                    e.recipesUsed = e.furnaceBurnTime;
                                }
                            }
                        }
                    } else {
                        if (!e.getItem(3).isEmpty() && e.getItem(3).getItem() instanceof ItemAugmentFuel) {
                            e.furnaceBurnTime = 2 * (getBurnTime(itemstack)) * e.getCookTime() / 200;
                        } else if (!e.getItem(3).isEmpty() && e.getItem(3).getItem() instanceof ItemAugmentSpeed) {
                            e.furnaceBurnTime = (getBurnTime(itemstack) / 2) * e.getCookTime() / 200;
                        } else {
                            e.furnaceBurnTime = getBurnTime(itemstack) * e.getCookTime() / 200;
                        }
                        e.recipesUsed = e.furnaceBurnTime;
                    }
                    if (e.isBurning()) {
                        flag1 = true;
                        if (!(itemstack.getItem() instanceof ItemHeater)) {
                            if (itemstack.hasContainerItem()) e.setItem(FUEL, itemstack.getContainerItem());
                            else if (!itemstack.isEmpty()) {
                                itemstack.shrink(1);
                                if (itemstack.isEmpty()) {
                                    e.setItem(FUEL, itemstack.getContainerItem());
                                }
                            }
                        }
                    }
                }
                if (e.isBurning() && valid) {
                    ++e.cookTime;
                    if (e.cookTime >= e.totalCookTime) {
                        e.cookTime = 0;
                        e.totalCookTime = e.getCookTime();
                        e.smeltItem(irecipe.orElse(null));
                        e.autoIO();
                        flag1 = true;
                    }
                } else {
                    e.cookTime = 0;
                }
            } else if (!e.isBurning() && e.cookTime > 0) {
                e.cookTime = clamp(e.cookTime - 2, 0, e.totalCookTime);
            }
            if (wasBurning != e.isBurning()) {
                flag1 = true;
                e.level.setBlock(e.worldPosition, e.level.getBlockState(e.worldPosition).setValue(BlockStateProperties.LIT, e.isBurning()), 3);
            }
            if (e.timer % 24 == 0) {

                if (e.cookTime <= 0)
                {

                    if (e.getItem(INPUT).isEmpty())
                    {
                        e.autoIO();
                        flag1 = true;
                    }
                    else if (e.getItem(INPUT).getCount() < e.getItem(INPUT).getMaxStackSize())
                    {
                        e.autoIO();
                        flag1 = true;
                    }
                    if (e.getItem(FUEL).isEmpty())
                    {
                        e.autoIO();
                        flag1 = true;
                    }
                    else if (e.getItem(FUEL).getCount() < e.getItem(FUEL).getMaxStackSize())
                    {
                        e.autoIO();
                        flag1 = true;
                    }
                }

                BlockState state = level.getBlockState(worldPosition);
                if (state.getValue(BlockIronFurnaceBase.TYPE) != e.getStateType()) {
                    level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.TYPE, e.getStateType()), 3);
                }
                if (state.getValue(BlockIronFurnaceBase.JOVIAL) != e.jovial) {
                    level.setBlock(worldPosition, state.setValue(BlockIronFurnaceBase.JOVIAL, e.jovial), 3);
                }


            }
        }

        if (flag1) {
            e.setChanged();
        }


        if (e instanceof BlockMillionFurnaceTile)
        {
            BlockMillionFurnaceTile furnaceTile = (BlockMillionFurnaceTile)e;
            if (!level.isClientSide)
            {
                if (!furnaceTile.furnaces_to_load.isEmpty())
                {
                    for (int i = 0; i < furnaceTile.furnaces_to_load.size(); i++)
                    {
                        furnaceTile.furnaces.add((BlockIronFurnaceTileBase) furnaceTile.level.getBlockEntity(furnaceTile.furnaces_to_load.get(i)));
                    }
                    furnaceTile.furnaces_to_load = Lists.newArrayList();
                }
            }


            boolean flag3 = true;
            if (!level.isClientSide) {

                if (furnaceTile.furnaces.size() == Config.millionFurnacePower.get()) {
                    for (BlockIronFurnaceTileBase furnace : furnaceTile.furnaces) {
                        level.getChunkAt(furnace.getBlockPos()).setLoaded(true);
                        if (furnace.cookTime <= 0) {
                            flag3 = false;
                        }
                    }
                }

                if (furnaceTile.furnaces.size() < Config.millionFurnacePower.get())
                {
                    flag3 = false;
                }

            }
            if (flag3) {
                furnaceTile.providing = true;
                if (!level.isClientSide) {
                    for (Direction dir : Direction.values()) {
                        BlockEntity tile = level.getBlockEntity(e.worldPosition.offset(dir.getNormal()));
                        if (tile != null) {
                            tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite()).ifPresent(h -> {
                                h.receiveEnergy(Config.millionFurnacePowerToGenerate.get(), false);
                            });
                        }
                    }
                }
            }
        }

    }

    public static int clamp(int p_76125_0_, int p_76125_1_, int p_76125_2_) {
        if (p_76125_0_ < p_76125_1_) {
            return p_76125_1_;
        } else {
            return p_76125_0_ > p_76125_2_ ? p_76125_2_ : p_76125_0_;
        }
    }

    protected void autoIO() {
        for (Direction dir : Direction.values()) {
            BlockEntity tile = level.getBlockEntity(worldPosition.offset(dir.getNormal()));
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

    @Nonnull
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!canPlaceItemThroughFace(slot, stack, null))
            return stack;

        ItemStack existing = this.getItem(slot);

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
                this.setItem(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
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


    protected int getStateType() {
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

    protected boolean canSmelt(@Nullable Recipe<?> recipe) {
        if (!this.getItem(0).isEmpty() && recipe != null) {
            ItemStack recipeOutput = recipe.getResultItem();
            if (!recipeOutput.isEmpty()) {
                ItemStack output = this.getItem(OUTPUT);
                if (output.isEmpty()) return true;
                else if (!output.sameItem(recipeOutput)) return false;
                else return output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize();
            }
        }
        return false;
    }

    protected void smeltItem(@Nullable Recipe<?> recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.getItem(INPUT);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.getItem(OUTPUT);
            if (itemstack2.isEmpty()) {
                this.setItem(OUTPUT, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }
            this.checkXP(recipe);
            if (!this.level.isClientSide) {
                this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.getItem(FUEL).isEmpty() && this.getItem(FUEL).getItem() == Items.BUCKET) {
                this.setItem(FUEL, new ItemStack(Items.WATER_BUCKET));
            }
            if (ModList.get().isLoaded("pmmo")) {
                FurnaceHandler.handleSmelted(itemstack, itemstack2, level, worldPosition, 0);
                if (recipe instanceof SmokingRecipe)
                {
                  FurnaceHandler.handleSmelted(itemstack, itemstack2, level, worldPosition, 1);
                }
            }
            itemstack.shrink(1);
        }
    }


    @Override
    public void load(CompoundTag tag) {
        this.furnaceBurnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.totalCookTime = tag.getInt("CookTimeTotal");
        this.timer = 0;
        this.currentAugment = tag.getInt("Augment");
        this.jovial = tag.getInt("Jovial");
        this.recipesUsed = this.getBurnTime(this.getItem(1));
        CompoundTag compoundnbt = tag.getCompound("RecipesUsed");

        for (String s : compoundnbt.getAllKeys()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
        this.furnaceSettings.read(tag);
        this.linkedPos = new BlockPos(tag.getInt("LinkedX"), tag.getInt("LinkedY"), tag.getInt("LinkedZ"));
        /**
         CompoundNBT energyTag = tag.getCompound("energy");
         energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
         **/

        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("BurnTime", this.furnaceBurnTime);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.totalCookTime);
        tag.putInt("Augment", this.currentAugment);
        tag.putInt("Jovial", this.jovial);
        this.furnaceSettings.write(tag);

        /**
         energy.ifPresent(h -> {
         CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
         tag.put("energy", compound);
         });
         **/

        CompoundTag compoundnbt = new CompoundTag();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            compoundnbt.putInt(recipeId.toString(), craftedAmount);
        });
        tag.put("RecipesUsed", compoundnbt);
        tag.putInt("LinkedX", this.linkedPos.getX());
        tag.putInt("LinkedY", this.linkedPos.getY());
        tag.putInt("LinkedZ", this.linkedPos.getZ());
    }

    protected static int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime(RecipeType.SMELTING);
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0) : ret, RecipeType.SMELTING);
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
            if (stack.isEmpty()) {
                return false;
            }

            return hasRecipe(stack);
        }
        if (index == FUEL) {
            ItemStack itemstack = this.getItem(FUEL);
            return getBurnTime(stack) > 0 || (stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET) || stack.getItem() instanceof ItemHeater;
        }
        return false;
    }

    public void setJovial(int value) {
        this.jovial = value;
    }

    public void checkXP(@Nullable Recipe<?> recipe)
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
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {

        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipes.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void unlockRecipes(Player player) {
        List<Recipe<?>> list = this.grantStoredRecipeExperience(player.level, new Vector3d(player.position().x, player.position().y, player.position().z));
        player.awardRecipes(list);
        this.recipes.clear();
    }

    public List<Recipe<?>> grantStoredRecipeExperience(Level level, Vector3d worldPosition) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((h) -> {
                list.add(h);
                splitAndSpawnExperience(level, worldPosition, entry.getIntValue(), ((AbstractCookingRecipe) h).getExperience());
            });
        }

        return list;
    }

    public static float frac(float p_226164_0_) {
        return p_226164_0_ - (float)Math.floor(p_226164_0_);
    }

    public static double frac(double p_181162_0_) {
        return p_181162_0_ - (double)Math.floor(p_181162_0_);
    }

    public static int floor(float p_76141_0_) {
        int i = (int)p_76141_0_;
        return p_76141_0_ < (float)i ? i - 1 : i;
    }

    private static void splitAndSpawnExperience(Level level, Vector3d worldPosition, int craftedAmount, float experience) {
        int i = floor((float) craftedAmount * experience);
        float f = frac((float) craftedAmount * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        while (i > 0) {
            int j = ExperienceOrb.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrb(level, worldPosition.x, worldPosition.y, worldPosition.z, j));
        }

    }

    @Override
    public void fillStackedContents(StackedContents helper) {
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
