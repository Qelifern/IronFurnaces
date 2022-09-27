package ironfurnaces.container.furnaces;

import ironfurnaces.IronFurnaces;
import ironfurnaces.container.slots.*;
import ironfurnaces.energy.FEnergyStorage;
import ironfurnaces.items.ItemHeater;
import ironfurnaces.items.augments.ItemAugmentBlasting;
import ironfurnaces.items.augments.ItemAugmentSmoking;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.util.container.FactoryDataSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public abstract class BlockIronFurnaceContainerBase extends AbstractContainerMenu {

    protected BlockIronFurnaceTileBase te;
    protected Player playerEntity;
    protected IItemHandler playerInventory;
    protected final Level world;

    public BlockIronFurnaceContainerBase(MenuType<?> containerType, int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(containerType, windowId);
        this.te = (BlockIronFurnaceTileBase) world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerInventory.player.level;

        //FURNACE
        this.addSlot(new SlotIronFurnaceInput(te, 0, 56, 17));
        this.addSlot(new SlotIronFurnaceFuel(te, 1, 56, 53));
        this.addSlot(new SlotIronFurnace(playerEntity, te, 2, 116, 35));
        this.addSlot(new SlotIronFurnaceAugmentRed(te, 3, 26, 35));
        this.addSlot(new SlotIronFurnaceAugmentGreen(te, 4, 80, 35));
        this.addSlot(new SlotIronFurnaceAugmentBlue(te, 5, 134, 35));

        //GENERATOR
        this.addSlot(new SlotIronFurnaceInputGenerator(te, 6, 56, 40));

        //FACTORY
        this.addSlot(new SlotIronFurnaceInputFactory(0, te, 7, 28, 6));
        this.addSlot(new SlotIronFurnaceInputFactory(1, te, 8, 49, 6));
        this.addSlot(new SlotIronFurnaceInputFactory(2, te, 9, 70, 6));
        this.addSlot(new SlotIronFurnaceInputFactory(3, te, 10, 91, 6));
        this.addSlot(new SlotIronFurnaceInputFactory(4, te, 11, 112, 6));
        this.addSlot(new SlotIronFurnaceInputFactory(5, te, 12, 133, 6));
        this.addSlot(new SlotIronFurnaceOutputFactory(0, playerEntity, te, 13, 28, 55));
        this.addSlot(new SlotIronFurnaceOutputFactory(1, playerEntity, te, 14, 49, 55));
        this.addSlot(new SlotIronFurnaceOutputFactory(2, playerEntity, te, 15, 70, 55));
        this.addSlot(new SlotIronFurnaceOutputFactory(3, playerEntity, te, 16, 91, 55));
        this.addSlot(new SlotIronFurnaceOutputFactory(4, playerEntity, te,17, 112, 55));
        this.addSlot(new SlotIronFurnaceOutputFactory(5, playerEntity, te, 18, 133, 55));
        layoutPlayerInventorySlots(8, 84);
        checkContainerSize(this.te, 19);
        addDataSlots();
    }

    public void addDataSlots()
    {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getAugmentGUI() ? 1 : 0;
            }

            @Override
            public void set(int value) {
                te.furnaceSettings.set(10, value);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getIsFurnace() ? 1 : 0;
            }

            @Override
            public void set(int value) {
                if (value == 1)
                {
                    te.currentAugment[2] = 0;
                }
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getIsGenerator() ? 1 : 0;
            }

            @Override
            public void set(int value) {
                if (value == 1)
                {
                    te.currentAugment[2] = 2;
                }
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getIsFactory() ? 1 : 0;
            }

            @Override
            public void set(int value) {
                if (value == 1)
                {
                    te.currentAugment[2] = 1;
                }
            }
        });
        addEnergyData();
        addFurnaceData();
        addGeneratorData();
        addFactoryData();

    }

    public void addFurnaceData()
    {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return te.furnaceBurnTime & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.furnaceBurnTime & 0xffff0000;
                te.furnaceBurnTime = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (te.furnaceBurnTime >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.furnaceBurnTime & 0x0000ffff;
                te.furnaceBurnTime = add | (value << 16);
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return te.recipesUsed & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.recipesUsed & 0xffff0000;
                te.recipesUsed = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (te.recipesUsed >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.recipesUsed & 0x0000ffff;
                te.recipesUsed = add | (value << 16);
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return te.cookTime & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.cookTime & 0xffff0000;
                te.cookTime = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (te.cookTime >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.cookTime & 0x0000ffff;
                te.cookTime = add | (value << 16);
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return te.totalCookTime & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.totalCookTime & 0xffff0000;
                te.totalCookTime = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (te.totalCookTime >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.totalCookTime & 0x0000ffff;
                te.totalCookTime = add | (value << 16);
            }
        });
    }

    public void addGeneratorData()
    {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (int)te.generatorBurn & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = (int)te.generatorBurn & 0xffff0000;
                te.generatorBurn = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return ((int)te.generatorBurn >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = (int)te.generatorBurn & 0x0000ffff;
                te.generatorBurn = add | (value << 16);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return te.generatorRecentRecipeRF & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.generatorRecentRecipeRF & 0xffff0000;
                te.generatorRecentRecipeRF = add + (value & 0xffff);
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (te.generatorRecentRecipeRF >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int add = te.generatorRecentRecipeRF & 0x0000ffff;
                te.generatorRecentRecipeRF = add | (value << 16);
            }
        });
    }

    public void addFactoryData()
    {
        for (int i = 0; i < te.factoryCookTime.length; i++)
        {
            addDataSlot(new FactoryDataSlot(i) {
                @Override
                public int get() {
                    return te.factoryCookTime[index] & 0xffff;
                }

                @Override
                public void set(int value) {
                    int add = te.factoryCookTime[index] & 0xffff0000;
                    te.factoryCookTime[index] = add + (value & 0xffff);
                }
            });
            addDataSlot(new FactoryDataSlot(i) {
                @Override
                public int get() {
                    return (te.factoryCookTime[index] >> 16) & 0xffff;
                }

                @Override
                public void set(int value) {
                    int add = te.factoryCookTime[index] & 0x0000ffff;
                    te.factoryCookTime[index] = add | (value << 16);
                }
            });
        }

        for (int i = 0; i < te.factoryTotalCookTime.length; i++)
        {
            addDataSlot(new FactoryDataSlot(i) {
                @Override
                public int get() {
                    return te.factoryTotalCookTime[index] & 0xffff;
                }

                @Override
                public void set(int value) {
                    int add = te.factoryTotalCookTime[index] & 0xffff0000;
                    te.factoryTotalCookTime[index] = add + (value & 0xffff);
                }
            });
            addDataSlot(new FactoryDataSlot(i) {
                @Override
                public int get() {
                    return (te.factoryTotalCookTime[index] >> 16) & 0xffff;
                }

                @Override
                public void set(int value) {
                    int add = te.factoryTotalCookTime[index] & 0x0000ffff;
                    te.factoryTotalCookTime[index] = add | (value << 16);
                }
            });
        }
    }

    public int getEnergy() {
        return te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy() {
        return te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    // Credit - Mcjty
    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void addEnergyData() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getMaxEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int capacity = h.getMaxEnergyStored() & 0xffff0000;
                    ((FEnergyStorage)h).setCapacity(capacity + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getMaxEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int capacity = h.getMaxEnergyStored() & 0x0000ffff;
                    ((FEnergyStorage)h).setCapacity(capacity | (value << 16));
                });
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((FEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((FEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    public boolean stillValid(Player player) {
        return this.te.stillValid(player);
    }

    public int getTier()
    {
        return te.getTier();

    }


    public boolean isAutoSplit()
    {
        return te.isAutoSplit();
    }



    public int getRedstoneMode() {
        return this.te.getRedstoneSetting();
    }


    public int getComSub() {
        return this.te.getRedstoneComSub();
    }


    public boolean getAutoInput() {
        return this.te.getAutoInput() == 1;
    }


    public boolean getAugmentGUI() {
        return this.te.getAugmentGUI() == 1;
    }


    public boolean getIsFactory() {
        return this.te.isFactory();
    }


    public boolean getIsFurnace() {
        return this.te.isFurnace();
    }
    
    public boolean getIsGenerator() {
        return this.te.isGenerator();
    }
    
    public boolean getAutoOutput() {
        return this.te.getAutoOutput() == 1;
    }
    
    public Component getTooltip(int index) {
        switch (te.furnaceSettings.get(index))
        {
            case 1:
                return Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".gui_input");
            case 2:
                return Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".gui_output");
            case 3:
                return Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".gui_input_output");
            case 4:
                return Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".gui_fuel");
            default:
                return Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".gui_none");
        }
    }


    public int getSettingTop()
    {
        return this.te.getSettingTop();
    }


    public int getSettingBottom()
    {
        return this.te.getSettingBottom();
    }


    public int getSettingFront()
    {
        return this.te.getSettingFront();
    }


    public int getSettingBack()
    {
        return this.te.getSettingBack();
    }


    public int getSettingLeft()
    {
        return this.te.getSettingLeft();
    }


    public int getSettingRight()
    {
        return this.te.getSettingRight();
    }


    public int getIndexFront()
    {
        return this.te.getIndexFront();
    }


    public int getIndexBack()
    {
        return this.te.getIndexBack();
    }


    public int getIndexLeft()
    {
        return this.te.getIndexLeft();
    }


    public int getIndexRight()
    {
        return this.te.getIndexRight();
    }


    public BlockPos getPos() {
        return this.te.getBlockPos();
    }


    public boolean isBurning() {
        return this.te.isBurning();
    }


    public int getCookScaled(int pixels) {
        int i = this.te.cookTime;
        int j = this.te.totalCookTime;
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }


    public int getFactoryCookScaled(int index, int pixels) {
        int i = this.te.factoryCookTime[index];
        int j = this.te.factoryTotalCookTime[index];
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }


    public int getFactoryCooktimeSize()
    {
        return this.te.factoryCookTime.length;
    }


    public int getBurnLeftScaled(int pixels) {
        int i = this.te.recipesUsed;
        if (i == 0) {
            i = 200;
        }

        return this.te.furnaceBurnTime * pixels / i;
    }


    public int getGeneratorBurnScaled(int pixels) {
        int i = this.te.generatorRecentRecipeRF;
        if (i == 0) {
            i = 200;
        }
        return (int)this.te.generatorBurn * pixels / i;
    }


    public boolean isGeneratorBurning()
    {
        return te.generatorBurn > 0;
    }


    public int getEnergyScaled(int pixels)
    {
        int i = this.te.getEnergy();
        int j = this.te.getCapacity();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (te.isGenerator())
            {
                if (index != 6 && index != 3 && index != 4 && index != 5)
                {
                    if (te.getItem(3).getItem() instanceof ItemAugmentSmoking)
                    {
                        if (te.getSmokingBurn(itemstack1) > 0) {
                            if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                    else if (te.getItem(3).getItem() instanceof ItemAugmentBlasting)
                    {
                        if (te.hasGeneratorBlastingRecipe(itemstack1)) {
                            if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                    else
                    {
                        if (BlockIronFurnaceTileBase.isItemFuel(itemstack1) && !(itemstack1.getItem() instanceof ItemHeater)) {
                            if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                    if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
                        if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
                        if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
                        if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 19 && index < 45) {
                        if (!this.moveItemStackTo(itemstack1, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 45 && index < 54 && !this.moveItemStackTo(itemstack1, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (te.isFactory()) {
                if (index >= 12 && index <= 18) {
                    if (!this.moveItemStackTo(itemstack1, 19, 54, true)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickCraft(itemstack1, itemstack);
                } else if (index >= 19) {
                     if (this.te.hasRecipe(itemstack1)) {
                         if (getTier() == 2)
                         {
                             if (!this.moveItemStackTo(itemstack1, 7, 13, false)) {
                                 return ItemStack.EMPTY;
                             }
                         }
                         else if (getTier() == 1)
                         {
                             if (!this.moveItemStackTo(itemstack1, 8, 12, false)) {
                                 return ItemStack.EMPTY;
                             }
                         }
                         else
                         {
                             if (!this.moveItemStackTo(itemstack1, 9, 11, false)) {
                                 return ItemStack.EMPTY;
                             }
                         }

                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
                        if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
                        if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
                        if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 19 && index < 45) {
                        if (!this.moveItemStackTo(itemstack1, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 45 && index < 54 && !this.moveItemStackTo(itemstack1, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (te.isFurnace())
            {

                if (index == 2) {
                    if (!this.moveItemStackTo(itemstack1, 19, 54, true)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickCraft(itemstack1, itemstack);
                } else if (index != 1 && index != 0 && index != 3 && index != 4 && index != 5) {
                    if (this.te.hasRecipe(itemstack1)) {
                        if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemFuel(itemstack1)) {
                        if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
                        if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
                        if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
                        if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 19 && index < 45) {
                        if (!this.moveItemStackTo(itemstack1, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 45 && index < 54 && !this.moveItemStackTo(itemstack1, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
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

}
