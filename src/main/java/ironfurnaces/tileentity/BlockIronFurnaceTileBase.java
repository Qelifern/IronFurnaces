package ironfurnaces.tileentity;

import com.google.common.collect.Lists;
import harmonised.pmmo.events.FurnaceHandler;
import ironfurnaces.IronFurnaces;
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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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

public abstract class BlockIronFurnaceTileBase extends TileEntityInventory implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {
    public final int[] provides = new int[Direction.values().length];
    private final int[] lastProvides = new int[this.provides.length];

    public static final int INPUT = 0;
    public static final int FUEL = 1;
    public static final int OUTPUT = 2;

    protected AbstractCookingRecipe curRecipe;

    public int show_inventory_settings;
    private int jovial;
    protected int timer;
    private int currentAugment; // 0 == none 1 == Blasting 2 == Smoking 3 == Speed 4 == Fuel
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    private int cookTime;
    private int totalCookTime = this.getCookTime();
    private int recipesUsed;
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

    public IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public FurnaceSettings furnaceSettings;

    @Override
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        this.markDirty();
        return new SUpdateTileEntityPacket(getPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        CompoundNBT tag = pkt.getNbtCompound();
        this.read(world.getBlockState(pos), tag);
        this.markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos).getBlock().getDefaultState(), world.getBlockState(pos), 2);
    }



    public BlockIronFurnaceTileBase(TileEntityType<?> tileentitytypeIn) {
        super(tileentitytypeIn, 4);
        this.recipeType = IRecipeType.SMELTING;
        furnaceSettings = new FurnaceSettings() {
            @Override
            public void onChanged() {
                markDirty();
            }
        };

    }

    protected int getCookTime() {
        ItemStack stack = this.getStackInSlot(3);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemAugmentSpeed) {
                return getSpeed() > 1 ? getSpeed() / 2 : getSpeed();
            }
            if (stack.getItem() instanceof ItemAugmentFuel) {
                return (int) Math.ceil(getSpeed() * 1.25);
            }
        }
        return getSpeed();

    }

    protected int getSpeed() {
        if (getCookTimeConfig() == null) {
            IronFurnaces.LOGGER.error("Illegal method call.");
            return 200;
        }
        int i = getCookTimeConfig().get();
        if (this.recipeType != null) {
            if (this.recipeType == IRecipeType.SMELTING) {
                int j = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse((i));
                if (j < i) {
                    int k = j - (200 - i);
                    return Math.max(k, 1);
                }
            } else {
                int j = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse((i));
                if (j < (i / 2)) {
                    int k = j - (200 - (i / 2));
                    return Math.max(k, 1);
                }
            }
        }
        return i;
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

        public int size() {
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
        BlockState state = world.getBlockState(pos);
        if (state.get(BlockStateProperties.LIT) != this.isBurning()) {
            world.setBlockState(pos, state.with(BlockStateProperties.LIT, this.isBurning()), 3);
        }
        if (state.get(BlockIronFurnaceBase.TYPE) != this.getStateType()) {
            world.setBlockState(pos, state.with(BlockIronFurnaceBase.TYPE, this.getStateType()), 3);
        }
        if (state.get(BlockIronFurnaceBase.JOVIAL) != this.jovial) {
            world.setBlockState(pos, state.with(BlockIronFurnaceBase.JOVIAL, this.jovial), 3);
        }
    }

    @Override
    public void tick() {
        if (furnaceSettings.size() <= 0)
        {
            furnaceSettings = new FurnaceSettings() {
                @Override
                public void onChanged() {
                    markDirty();
                }
            };
        }
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
            int mode = this.getRedstoneSetting();
            if (mode != 0) {
                if (mode == 2) {
                    int i = 0;
                    for (Direction side : Direction.values()) {
                        if (world.getRedstonePower(pos.offset(side), side) > 0) {
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

                        if (world.getRedstonePower(pos.offset(side), side) > 0) {
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
                    this.provides[i] = getBlockState().getStrongPower(this.world, pos, Direction.byIndex(i));

            } else {
                for (int i = 0; i < Direction.values().length; i++)
                    this.provides[i] = 0;
            }
            if (this.doesNeedUpdateSend()) {
                this.onUpdateSent();
            }
            ItemStack stack = this.getStackInSlot(3);
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
                AbstractCookingRecipe irecipe = world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).orElse(null);
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
                                if (energy >= 2000) {
                                    ((BlockWirelessEnergyHeaterTile) te).removeEnergy(2000);
                                    if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentFuel) {
                                        this.furnaceBurnTime = 400 * this.getCookTime() / 200;
                                    } else if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() instanceof ItemAugmentSpeed) {
                                        this.furnaceBurnTime = 100 * this.getCookTime() / 200;
                                    } else {
                                        this.furnaceBurnTime = 200 * this.getCookTime() / 200;
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
                if (state.get(BlockIronFurnaceBase.TYPE) != this.getStateType()) {
                    world.setBlockState(pos, state.with(BlockIronFurnaceBase.TYPE, this.getStateType()), 3);
                }
                if (state.get(BlockIronFurnaceBase.JOVIAL) != this.jovial) {
                    world.setBlockState(pos, state.with(BlockIronFurnaceBase.JOVIAL, this.jovial), 3);
                }
                autoIO();
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private void autoIO() {
        for (Direction dir : Direction.values()) {
            if (this.furnaceSettings.get(dir.getIndex()) == 1 || this.furnaceSettings.get(dir.getIndex()) == 2 || this.furnaceSettings.get(dir.getIndex()) == 3 || this.furnaceSettings.get(dir.getIndex()) == 4) {
                TileEntity tile = world.getTileEntity(pos.offset(dir));
                if (tile != null) {
                    IItemHandler other = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).map(other1 -> other1).orElse(null);

                    if (other != null) {
                        if (this.getAutoInput() != 0 || this.getAutoOutput() != 0) {
                            for (int i = 0; i < other.getSlots(); i++) {
                                if (this.getAutoInput() == 1) {
                                    if (this.furnaceSettings.get(dir.getIndex()) == 1 || this.furnaceSettings.get(dir.getIndex()) == 3) {
                                        ItemStack stack = other.extractItem(i, 64, true);
                                        if (hasRecipe(stack) && getStackInSlot(INPUT).isEmpty() || ItemHandlerHelper.canItemStacksStack(getStackInSlot(INPUT), stack)) {
                                            insertItemInternal(INPUT, other.extractItem(i, 64 - this.getStackInSlot(INPUT).getCount(), false), false);
                                        }
                                    }
                                    if (this.furnaceSettings.get(dir.getIndex()) == 4) {
                                        ItemStack stack = other.extractItem(i, 64, true);
                                        if (isItemFuel(stack) && getStackInSlot(FUEL).isEmpty() || ItemHandlerHelper.canItemStacksStack(getStackInSlot(FUEL), stack)) {
                                            insertItemInternal(FUEL, other.extractItem(i, 64 - this.getStackInSlot(FUEL).getCount(), false), false);
                                        }
                                    }
                                }
                                if (this.getAutoOutput() == 1) {
                                    if (this.furnaceSettings.get(dir.getIndex()) == 2 || this.furnaceSettings.get(dir.getIndex()) == 3) {
                                        ItemStack stack = extractItemInternal(OUTPUT, 64 - other.getStackInSlot(i).getCount(), true);
                                        if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= 64))) {
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
        return this.world.getRecipeManager().getRecipe((IRecipeType)this.recipeType, new Inventory(stack), this.world).isPresent();
    }

    @Nonnull
    public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValidForSlot(slot, stack))
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
            this.markDirty();
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    private ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existing = this.getStackInSlot(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.setInventorySlotContents(slot, ItemStack.EMPTY);
                this.markDirty();
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.setInventorySlotContents(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                this.markDirty();
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
        int i = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).getIndex();
        return this.furnaceSettings.get(i);
    }

    public int getSettingBack() {
        int i = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).getOpposite().getIndex();
        return this.furnaceSettings.get(i);
    }

    public int getSettingLeft() {
        Direction facing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return this.furnaceSettings.get(Direction.EAST.getIndex());
        } else if (facing == Direction.WEST) {
            return this.furnaceSettings.get(Direction.NORTH.getIndex());
        } else if (facing == Direction.SOUTH) {
            return this.furnaceSettings.get(Direction.WEST.getIndex());
        } else {
            return this.furnaceSettings.get(Direction.SOUTH.getIndex());
        }
    }

    public int getSettingRight() {
        Direction facing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return this.furnaceSettings.get(Direction.WEST.getIndex());
        } else if (facing == Direction.WEST) {
            return this.furnaceSettings.get(Direction.SOUTH.getIndex());
        } else if (facing == Direction.SOUTH) {
            return this.furnaceSettings.get(Direction.EAST.getIndex());
        } else {
            return this.furnaceSettings.get(Direction.NORTH.getIndex());
        }
    }

    public int getIndexFront() {
        int i = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).getIndex();
        return i;
    }

    public int getIndexBack() {
        int i = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).getOpposite().getIndex();
        return i;
    }

    public int getIndexLeft() {
        Direction facing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return Direction.EAST.getIndex();
        } else if (facing == Direction.WEST) {
            return Direction.NORTH.getIndex();
        } else if (facing == Direction.SOUTH) {
            return Direction.WEST.getIndex();
        } else {
            return Direction.SOUTH.getIndex();
        }
    }

    public int getIndexRight() {
        Direction facing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (facing == Direction.NORTH) {
            return Direction.WEST.getIndex();
        } else if (facing == Direction.WEST) {
            return Direction.SOUTH.getIndex();
        } else if (facing == Direction.SOUTH) {
            return Direction.EAST.getIndex();
        } else {
            return Direction.NORTH.getIndex();
        }
    }

    public int getAutoInput()
    {
        return this.furnaceSettings.get(6);
    }

    public int getAutoOutput()
    {
        return this.furnaceSettings.get(7);
    }

    public int getRedstoneSetting()
    {
        return this.furnaceSettings.get(8);
    }

    public int getRedstoneComSub()
    {
        return this.furnaceSettings.get(9);
    }



    private int getStateType() {
        if (this.getStackInSlot(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
            return 1;
        } else if (this.getStackInSlot(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
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

    protected void smeltItem(@Nullable IRecipe<?> recipe) {
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
            if (ModList.get().isLoaded("pmmo"))
            {
                FurnaceHandler.handleSmelted(itemstack, itemstack2, world, pos, 0);
                FurnaceHandler.handleSmelted(itemstack, itemstack2, world, pos, 1);
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

        for (String s : compoundnbt.keySet()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
        this.show_inventory_settings = tag.getInt("ShowInvSettings");
        this.furnaceSettings.read(tag);
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
        tag.putInt("ShowInvSettings", this.show_inventory_settings);
        this.furnaceSettings.write(tag);
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

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] invHandlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

    @Nonnull
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {

        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
        if (this.furnaceSettings.get(side.getIndex()) == 0) {
            return new int[]{};
        } else if (this.furnaceSettings.get(side.getIndex()) == 1) {
            return new int[]{0, 1};
        } else if (this.furnaceSettings.get(side.getIndex()) == 2) {
            return new int[]{2};
        } else if (this.furnaceSettings.get(side.getIndex()) == 3) {
            return new int[]{0, 1, 2};
        } else if (this.furnaceSettings.get(side.getIndex()) == 4) {
            return new int[]{1};
        }
        return new int[]{};
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        if (this.furnaceSettings.get(direction.getIndex()) == 0) {
            return false;
        } else if (this.furnaceSettings.get(direction.getIndex()) == 1) {
            return false;
        } else if (this.furnaceSettings.get(direction.getIndex()) == 2) {
            return index == 2;
        } else if (this.furnaceSettings.get(direction.getIndex()) == 3) {
            return index == 2;
        } else if (this.furnaceSettings.get(direction.getIndex()) == 4) {
            return false;
        }
        return false;
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == OUTPUT || index == 3) {
            return false;
        }
        if (index == INPUT) {
            return this.world.getRecipeManager().getRecipe((IRecipeType) this.recipeType, new Inventory(stack), this.world).isPresent();
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
        int i = MathHelper.floor((float) craftedAmount * experience);
        float f = MathHelper.frac((float) craftedAmount * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        while (i > 0) {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            world.addEntity(new ExperienceOrbEntity(world, pos.x, pos.y, pos.z, j));
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
        this.world.notifyNeighborsOfStateChange(this.pos, getBlockState().getBlock());
    }


}
