package ironfurnaces.container;

import ironfurnaces.IronFurnaces;
import ironfurnaces.items.ItemAugmentBlasting;
import ironfurnaces.items.ItemAugmentSmoking;
import ironfurnaces.tileentity.BlockIronFurnaceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public abstract class BlockIronFurnaceContainerBase extends Container {

    protected BlockIronFurnaceTileBase te;
    protected IIntArray fields;
    protected PlayerEntity playerEntity;
    protected IItemHandler playerInventory;
    protected final World world;
    private IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public BlockIronFurnaceContainerBase(ContainerType<?> containerType, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(containerType, windowId, world, pos, playerInventory, player, new IntArray(5));
    }

    public BlockIronFurnaceContainerBase(ContainerType<?> containerType, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray fields) {
        super(containerType, windowId);
        this.te = (BlockIronFurnaceTileBase) world.getTileEntity(pos);
        this.recipeType = te.recipeType;
        assertInventorySize(this.te, 4);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerInventory.player.world;
        this.fields = fields;
        assertIntArraySize(this.fields, 5);
        this.trackIntArray(this.fields);

        this.addSlot(new Slot(te, 0, 56, 17));
        this.addSlot(new SlotIronFurnaceFuel(this.te, 1, 56, 53));
        this.addSlot(new SlotIronFurnace(playerEntity, te, 2, 116, 35));
        this.addSlot(new SlotIronFurnaceAugment(te, 3, 26, 35));
        layoutPlayerInventorySlots(8, 84);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean showInventoryButtons() {
        return this.te.fields.get(4) == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRedstoneMode() {
        return this.te.getRedstoneSetting();
    }

    @OnlyIn(Dist.CLIENT)
    public int getComSub() {
        return this.te.getRedstoneComSub();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoInput() {
        return this.te.getAutoInput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoOutput() {
        return this.te.getAutoOutput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getTooltip(int index) {
        switch (te.furnaceSettings.get(index))
        {
            case 1:
                return new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_input");
            case 2:
                return new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_output");
            case 3:
                return new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_input_output");
            case 4:
                return new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_fuel");
            default:
                return new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_none");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingTop()
    {
        return this.te.getSettingTop();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBottom()
    {
        return this.te.getSettingBottom();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingFront()
    {
        return this.te.getSettingFront();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBack()
    {
        return this.te.getSettingBack();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingLeft()
    {
        return this.te.getSettingLeft();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingRight()
    {
        return this.te.getSettingRight();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexFront()
    {
        return this.te.getIndexFront();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexBack()
    {
        return this.te.getIndexBack();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexLeft()
    {
        return this.te.getIndexLeft();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexRight()
    {
        return this.te.getIndexRight();
    }

    @OnlyIn(Dist.CLIENT)
    public BlockPos getPos() {
        return this.te.getPos();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.te.isBurning();
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookScaled(int pixels) {
        int i = this.fields.get(2);
        int j = this.fields.get(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled(int pixels) {
        int i = this.fields.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.fields.get(0) * pixels / i;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        this.te.fields.set(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0 && index != 3) {
                if (this.hasRecipe(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (BlockIronFurnaceTileBase.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }



    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    protected boolean hasRecipe(ItemStack stack) {
        ItemStack augment = this.getInventory().get(3);
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
}
