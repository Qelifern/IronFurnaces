package ironfurnaces.gui;

import ironfurnaces.container.ContainerIronFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiIronFurnace extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/furnace.png");
    private TileEntityIronFurnaceBase te;
    private InventoryPlayer playerInv;

    public GuiIronFurnace(InventoryPlayer invPlayer, TileEntityIronFurnaceBase te) {
        super(new ContainerIronFurnace(invPlayer, te));

        xSize = 176;
        ySize = 166;

        this.te = te;
        this.playerInv = invPlayer;
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedComponentText(), 7, this.ySize - 93, 4210752);
        this.fontRenderer.drawString(te.getName().getUnformattedComponentText(), 7 + this.xSize / 2 - this.fontRenderer.getStringWidth(te.getName().getUnformattedComponentText()) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        if (TileEntityIronFurnaceBase.isBurning(te)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);

            int l = this.getCookProgressScaled(24);
            this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
        }
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.te.getField(2);
        int j = this.te.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;

    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.te.getField(1);
        if (i == 0) {
            i = 200;
        }

        return this.te.getField(0) * pixels / i;
    }

}
