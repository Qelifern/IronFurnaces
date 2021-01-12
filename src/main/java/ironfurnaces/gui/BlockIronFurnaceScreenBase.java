package ironfurnaces.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockIronFurnaceContainerBase;
import ironfurnaces.network.Messages;
import ironfurnaces.network.PacketSettingsButton;
import ironfurnaces.network.PacketShowSettingsButton;
import ironfurnaces.util.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class BlockIronFurnaceScreenBase<T extends BlockIronFurnaceContainerBase> extends ContainerScreen<T> {

    public ResourceLocation GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" + "textures/gui/furnace.png");
    public static final ResourceLocation WIDGETS = new ResourceLocation(IronFurnaces.MOD_ID + ":" + "textures/gui/widgets.png");
    PlayerInventory playerInv;
    ITextComponent name;


    public boolean add_button;
    public boolean sub_button;

    public BlockIronFurnaceScreenBase(T t, PlayerInventory inv, ITextComponent name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrix, int mouseX, int mouseY) {
        //drawString(Minecraft.getInstance().fontRenderer, "Energy: " + container.getEnergy(), 10, 10, 0xffffff);
        this.minecraft.fontRenderer.func_243248_b(matrix, this.playerInv.getDisplayName(), 7, this.ySize - 93, 4210752);
        this.minecraft.fontRenderer.func_243248_b(matrix, name, 7 + this.xSize / 2 - this.minecraft.fontRenderer.getStringWidth(name.getString()) / 2, 6, 4210752);

        if (this.container.showInventoryButtons() && this.container.getRedstoneMode() == 4) {
            int comSub = this.container.getComSub();
            int i = comSub > 9 ? 28 : 31;
            this.minecraft.fontRenderer.func_243248_b(matrix, new StringTextComponent("" + comSub), i - 42, 90, 4210752);
        }
        int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
        int actualMouseY = mouseY - ((this.height - this.ySize) / 2);

        this.addTooltips(matrix, actualMouseX, actualMouseY);

    }

    private void addTooltips(MatrixStack matrix, int mouseX, int mouseY) {

        if (!container.showInventoryButtons()) {
            if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                this.renderTooltip(matrix, new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_open"), mouseX, mouseY);
            }
        } else {
            if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                this.func_243308_b(matrix, StringHelper.getShiftInfoGui(), mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_auto_input"));
                list.add(new StringTextComponent("" + this.container.getAutoInput()));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_auto_output"));
                list.add(new StringTextComponent("" + this.container.getAutoOutput()));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 31 && mouseY <= 40) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_top"));
                list.add(this.container.getTooltip(1));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 55 && mouseY <= 64) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_bottom"));
                list.add(this.container.getTooltip(0));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 43 && mouseY <= 52) {
                List<ITextComponent> list = Lists.newArrayList();
                if (isShiftKeyDown()) {
                    list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_reset"));
                } else {
                    list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_front"));
                    list.add(this.container.getTooltip(this.container.getIndexFront()));
                }
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -44 && mouseX <= -35 && mouseY >= 43 && mouseY <= 52) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_left"));
                list.add(this.container.getTooltip(this.container.getIndexLeft()));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 43 && mouseY <= 52) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_right"));
                list.add(this.container.getTooltip(this.container.getIndexRight()));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 55 && mouseY <= 64) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_back"));
                list.add(this.container.getTooltip(this.container.getIndexBack()));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 70 && mouseY <= 83) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_redstone_ignored"));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -31 && mouseX <= -18 && mouseY >= 70 && mouseY <= 83) {
                List<ITextComponent> list = Lists.newArrayList();
                if (isShiftKeyDown()) {
                    list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_redstone_low"));
                } else {
                    list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_redstone_high"));
                }
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -15 && mouseX <= -2 && mouseY >= 70 && mouseY <= 83) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_redstone_comparator"));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 86 && mouseY <= 99) {
                List<ITextComponent> list = Lists.newArrayList();
                list.add(new TranslationTextComponent("tooltip." + IronFurnaces.MOD_ID + ".gui_redstone_comparator_sub"));
                this.func_243308_b(matrix, list, mouseX, mouseY);
            }

        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrix, relX, relY, 0, 0, this.xSize, this.ySize);
        int i;
        if (((BlockIronFurnaceContainerBase) this.container).isBurning()) {
            i = ((BlockIronFurnaceContainerBase) this.container).getBurnLeftScaled(13);
            this.blit(matrix, guiLeft + 56, guiTop + 36 + 12 - i, 176, 12 - i, 14, i + 1);
        }

        i = ((BlockIronFurnaceContainerBase) this.container).getCookScaled(24);
        this.blit(matrix, guiLeft + 79, guiTop + 34, 176, 14, i + 1, 16);

        this.minecraft.getTextureManager().bindTexture(WIDGETS);
        int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
        int actualMouseY = mouseY - ((this.height - this.ySize) / 2);

        this.addInventoryButtons(matrix, ((BlockIronFurnaceContainerBase) this.container), actualMouseX, actualMouseY);
        this.addRedstoneButtons(matrix, ((BlockIronFurnaceContainerBase) this.container), actualMouseX, actualMouseY);

    }


    private void addRedstoneButtons(MatrixStack matrix, BlockIronFurnaceContainerBase container, int mouseX, int mouseY) {
        if (this.container.showInventoryButtons()) {
            this.blitRedstone(matrix);
            if (this.container.getRedstoneMode() == 4) {
                int comSub = container.getComSub();
                boolean flag = isShiftKeyDown();
                if (flag) {
                    if (comSub > 0) {
                        this.sub_button = true;
                        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
                            this.blit(matrix, guiLeft - 31, guiTop + 86, 14, 0, 14, 14);
                        } else {
                            this.blit(matrix, guiLeft - 31, guiTop + 86, 0, 0, 14, 14);
                        }
                    } else {
                        this.sub_button = false;
                        this.blit(matrix, guiLeft - 31, guiTop + 86, 28, 0, 14, 14);
                    }

                } else {
                    if (comSub < 15) {
                        this.add_button = true;
                        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
                            this.blit(matrix, guiLeft - 31, guiTop + 86, 14, 14, 14, 14);
                        } else {
                            this.blit(matrix, guiLeft - 31, guiTop + 86, 0, 14, 14, 14);
                        }
                    } else {
                        this.add_button = false;
                        this.blit(matrix, guiLeft - 31, guiTop + 86, 28, 14, 14, 14);

                    }
                }
            }
        }
    }

    private void addInventoryButtons(MatrixStack matrix, BlockIronFurnaceContainerBase container, int mouseX, int mouseY) {
        if (!container.showInventoryButtons()) {
            this.blit(matrix, guiLeft - 20, guiTop + 4, 0, 28, 23, 26);
        } else if (container.showInventoryButtons()) {
            this.blit(matrix, guiLeft - 56, guiTop + 4, 0, 54, 59, 107);
            if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25 || this.container.getAutoInput()) {
                this.blit(matrix, guiLeft - 47, guiTop + 12, 0, 189, 14, 14);
            }
            if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25 || this.container.getAutoOutput()) {
                this.blit(matrix, guiLeft - 29, guiTop + 12, 14, 189, 14, 14);
            }
            this.blitIO(matrix);
        }


    }

    private void blitRedstone(MatrixStack matrix) {
        boolean flag = isShiftKeyDown();
        if (flag) {
            this.blit(matrix, guiLeft - 31, guiTop + 70, 84, 189, 14, 14);
        }
        int setting = this.container.getRedstoneMode();
        if (setting == 0) {
            this.blit(matrix, guiLeft - 47, guiTop + 70, 28, 189, 14, 14);
        } else if (setting == 1 && !flag) {
            this.blit(matrix, guiLeft - 31, guiTop + 70, 42, 189, 14, 14);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 31, guiTop + 70, 98, 189, 14, 14);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 15, guiTop + 70, 56, 189, 14, 14);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 47, guiTop + 86, 70, 189, 14, 14);
        }

    }

    private void blitIO(MatrixStack matrix) {
        int[] settings = new int[]{0, 0, 0, 0, 0, 0};
        int setting = this.container.getSettingTop();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 32, guiTop + 31, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 32, guiTop + 31, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 32, guiTop + 31, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 32, guiTop + 31, 30, 161, 10, 10);
        }
        settings[1] = setting;

        setting = this.container.getSettingBottom();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 32, guiTop + 55, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 32, guiTop + 55, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 32, guiTop + 55, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 32, guiTop + 55, 30, 161, 10, 10);
        }
        settings[0] = setting;
        setting = this.container.getSettingFront();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 32, guiTop + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 32, guiTop + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 32, guiTop + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 32, guiTop + 43, 30, 161, 10, 10);
        }
        settings[this.container.getIndexFront()] = setting;
        setting = this.container.getSettingBack();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 20, guiTop + 55, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 20, guiTop + 55, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 20, guiTop + 55, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 20, guiTop + 55, 30, 161, 10, 10);
        }
        settings[this.container.getIndexBack()] = setting;
        setting = this.container.getSettingLeft();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 44, guiTop + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 44, guiTop + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 44, guiTop + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 44, guiTop + 43, 30, 161, 10, 10);
        }
        settings[this.container.getIndexLeft()] = setting;
        setting = this.container.getSettingRight();
        if (setting == 1) {
            this.blit(matrix, guiLeft - 20, guiTop + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, guiLeft - 20, guiTop + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, guiLeft - 20, guiTop + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, guiLeft - 20, guiTop + 43, 30, 161, 10, 10);
        }
        settings[this.container.getIndexRight()] = setting;
        boolean input = false;
        boolean output = false;
        boolean both = false;
        boolean fuel = false;
        for (int set : settings) {
            if (set == 1) {
                input = true;
            } else if (set == 2) {
                output = true;
            } else if (set == 3) {
                both = true;
            } else if (set == 4) {
                fuel = true;
            }
        }
        if (input || both) {
            this.blit(matrix, guiLeft + 55, guiTop + 16, 0, 171, 18, 18);
        }
        if (output || both) {
            this.blit(matrix, guiLeft + 111, guiTop + 30, 0, 203, 26, 26);
        }
        if (fuel) {
            this.blit(matrix, guiLeft + 55, guiTop + 52, 18, 171, 18, 18);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double actualMouseX = mouseX - ((this.width - this.xSize) / 2);
        double actualMouseY = mouseY - ((this.height - this.ySize) / 2);
        this.mouseClickedRedstoneButtons(actualMouseX, actualMouseY);
        this.mouseClickedInventoryButtons(button, this.container, actualMouseX, actualMouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseClickedInventoryButtons(int button, BlockIronFurnaceContainerBase container, double mouseX, double mouseY) {
        boolean flag = button == GLFW.GLFW_MOUSE_BUTTON_2;
        if (!container.showInventoryButtons()) {
            if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                Messages.INSTANCE.sendToServer(new PacketShowSettingsButton(this.container.getPos(), 1));
            }
        } else {
            if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                Messages.INSTANCE.sendToServer(new PacketShowSettingsButton(this.container.getPos(), 0));
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25) {
                if (!this.container.getAutoInput()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 6, 1));
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                } else {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 6, 0));
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                }

            } else if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25) {
                if (!this.container.getAutoOutput()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 7, 1));
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                } else {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 7, 0));
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 31 && mouseY <= 40) {
                if (flag) {
                    sendToServerInverted(this.container.getSettingTop(), 1);
                } else {
                    sendToServer(this.container.getSettingTop(), 1);
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 55 && mouseY <= 64) {
                if (flag) {
                    sendToServerInverted(this.container.getSettingBottom(), 0);
                } else {
                    sendToServer(this.container.getSettingBottom(), 0);
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 43 && mouseY <= 52) {
                if (isShiftKeyDown()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 0, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 1, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 2, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 3, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 4, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 5, 0));
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.8F, 0.3F));
                } else {
                    if (flag) {
                        sendToServerInverted(this.container.getSettingFront(), this.container.getIndexFront());
                    } else {
                        sendToServer(this.container.getSettingFront(), this.container.getIndexFront());
                    }
                }
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 55 && mouseY <= 64) {
                if (flag) {
                    sendToServerInverted(this.container.getSettingBack(), this.container.getIndexBack());
                } else {
                    sendToServer(this.container.getSettingBack(), this.container.getIndexBack());
                }
            } else if (mouseX >= -44 && mouseX <= -35 && mouseY >= 43 && mouseY <= 52) {
                if (flag) {
                    sendToServerInverted(this.container.getSettingLeft(), this.container.getIndexLeft());
                } else {
                    sendToServer(this.container.getSettingLeft(), this.container.getIndexLeft());
                }
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 43 && mouseY <= 52) {
                if (flag) {
                    sendToServerInverted(this.container.getSettingRight(), this.container.getIndexRight());
                } else {
                    sendToServer(this.container.getSettingRight(), this.container.getIndexRight());
                }
            }
        }
    }

    private void sendToServer(int setting, int index) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
        if (setting <= 0) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 1));
        } else if (setting == 1) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 2));
        } else if (setting == 2) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 3));
        } else if (setting == 3) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 4));
        } else if (setting >= 4) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 0));
        }
    }

    private void sendToServerInverted(int setting, int index) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.3F, 0.3F));
        if (setting <= 0) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 4));
        } else if (setting == 1) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 0));
        } else if (setting == 2) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 1));
        } else if (setting == 3) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 2));
        } else if (setting >= 4) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), index, 3));
        }
    }

    public void mouseClickedRedstoneButtons(double mouseX, double mouseY) {
        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
            if (this.sub_button && isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 9, this.container.getComSub() - 1));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.3F, 0.3F));
            }
        }
        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
            if (this.add_button && !isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 9, this.container.getComSub() + 1));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
        if (mouseX >= -47 && mouseX <= -34 && mouseY >= 70 && mouseY <= 83) {
            if (this.container.getRedstoneMode() != 0) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 8, 0));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 70 && mouseY <= 83) {
            if (this.container.getRedstoneMode() != 1 && !isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 8, 1));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
            if (this.container.getRedstoneMode() != 2 && isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 8, 2));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -15 && mouseX <= -2 && mouseY >= 70 && mouseY <= 83) {
            if (this.container.getRedstoneMode() != 3) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 8, 3));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -47 && mouseX <= -34 && mouseY >= 86 && mouseY <= 99) {
            if (this.container.getRedstoneMode() != 4) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.container.getPos(), 8, 4));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
    }

    public static boolean isShiftKeyDown() {
        return isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isKeyDown(int glfw) {
        InputMappings.Input key = InputMappings.Type.KEYSYM.getOrMakeInput(glfw);
        int keyCode = key.getKeyCode();
        if (keyCode != InputMappings.INPUT_INVALID.getKeyCode()) {
            long windowHandle = Minecraft.getInstance().getMainWindow().getHandle();
            try {
                if (key.getType() == InputMappings.Type.KEYSYM) {
                    return InputMappings.isKeyDown(windowHandle, keyCode);
                } /**else if (key.getType() == InputMappings.Type.MOUSE) {
                 return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                 }**/
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
