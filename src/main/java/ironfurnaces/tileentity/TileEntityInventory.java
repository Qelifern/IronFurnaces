package ironfurnaces.tileentity;

import ironfurnaces.proxy.IGuiTile;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;

public abstract class TileEntityInventory extends TileEntity implements ISidedInventory, IInteractionObject, IGuiTile, IInventoryTile {

    protected NonNullList<ItemStack> inventory;
    protected ITextComponent name;

    public TileEntityInventory(TileEntityType<?> tileEntityTypeIn, int sizeInventory) {
        super(tileEntityTypeIn);
        inventory = NonNullList.withSize(sizeInventory, ItemStack.EMPTY);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return this.IgetSlotsForFace(side);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return IcanExtractItem(index, stack, direction);
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
         return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        if (compound.contains("CustomName", 8)) {
            this.name = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        if (this.name != null) {
            compound.setString("CustomName", ITextComponent.Serializer.toJson(this.name));
        }
        return compound;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.IisItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return this.IgetField(id);
    }

    @Override
    public void setField(int id, int value) {
        this.IsetField(id, value);
    }

    @Override
    public int getFieldCount() {
        return this.IgetFieldCount();
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public ITextComponent getName() {
        return (this.name != null ? this.name : new TextComponentTranslation(IgetName()));
    }

    @Override
    public boolean hasCustomName() {
        return this.name != null;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return this.name;
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return this.IcreateGui(player.inventory, this);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return this.IcreateContainer(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return this.IgetGuiID();
    }

}
