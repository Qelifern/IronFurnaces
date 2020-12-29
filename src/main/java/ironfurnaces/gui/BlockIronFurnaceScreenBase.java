package ironfurnaces.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockIronFurnaceContainerBase;
import ironfurnaces.network.Messages;
import ironfurnaces.network.PacketButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BlockIronFurnaceScreenBase<T extends BlockIronFurnaceContainerBase> extends ContainerScreen<T> {

    public ResourceLocation GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" +"textures/gui/furnace.png");
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

        if (((BlockIronFurnaceContainerBase)this.container).hasRedstoneAugment()) {
            int comSub = this.container.getComSub();
            int i = comSub > 9 ? 28 : 31;
            this.minecraft.fontRenderer.func_243248_b(matrix, new StringTextComponent("" + comSub), i, 25, 4210752);
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
        if (((BlockIronFurnaceContainerBase)this.container).isBurning()) {
            i = ((BlockIronFurnaceContainerBase)this.container).getBurnLeftScaled(13);
            this.blit(matrix, guiLeft + 56, guiTop + 36 + 12 - i, 176, 12 - i, 14, i + 1);
        }

        i = ((BlockIronFurnaceContainerBase)this.container).getCookScaled(24);
        this.blit(matrix, guiLeft + 79, guiTop + 34, 176, 14, i + 1, 16);

        if (((BlockIronFurnaceContainerBase)this.container).hasRedstoneAugment())
        {
            int comSub = ((BlockIronFurnaceContainerBase)this.container).getComSub();
            int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
            int actualMouseY = mouseY - ((this.height - this.ySize) / 2);
            if (comSub > 0)
            {
                this.sub_button = true;
                if(actualMouseX >= 19 && actualMouseX <= 33 && actualMouseY >= 56 && actualMouseY <= 70) {
                    this.blit(matrix, guiLeft + 19, guiTop + 56, 190, 31, 14, 14);
                }
                else
                {
                    this.blit(matrix, guiLeft + 19, guiTop + 56, 176, 31, 14, 14);
                }
            }
            else
            {
                this.sub_button = false;
                this.blit(matrix, guiLeft + 19, guiTop + 56, 204, 31, 14, 14);
            }
            if (comSub < 15) {
                this.add_button = true;
                if (actualMouseX >= 35 && actualMouseX <= 49 && actualMouseY >= 56 && actualMouseY <= 70) {
                    this.blit(matrix, guiLeft + 35, guiTop + 56, 190, 45, 14, 14);
                } else {
                    this.blit(matrix, guiLeft + 35, guiTop + 56, 176, 45, 14, 14);
                }
            }
            else
            {
                this.add_button = false;
                this.blit(matrix, guiLeft + 35, guiTop + 56, 204, 45, 14, 14);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int actualMouseX = (int) mouseX - ((this.width - this.xSize) / 2);
        int actualMouseY = (int) mouseY - ((this.height - this.ySize) / 2);
        if (actualMouseX >= 19 && actualMouseX <= 33 && actualMouseY >= 56 && actualMouseY <= 70)
        {
            if (this.sub_button)
            {
                Messages.INSTANCE.sendToServer(new PacketButton(this.container.getPos(), -1));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
        if (actualMouseX >= 35 && actualMouseX <= 49 && actualMouseY >= 56 && actualMouseY <= 70)
        {
            if (this.add_button)
            {
                Messages.INSTANCE.sendToServer(new PacketButton(this.container.getPos(), 1));
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
