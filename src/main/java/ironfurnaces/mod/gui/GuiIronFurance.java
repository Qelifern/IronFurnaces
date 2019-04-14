package ironfurnaces.mod.gui;

import ironfurnaces.mod.IronFurnaces;
import ironfurnaces.mod.container.ContainerIronFurance;
import ironfurnaces.mod.tileentity.TileEntityIronFurnaceBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiIronFurance extends GuiContainer{

	private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(IronFurnaces.MOD_ID + ":" + "textures/gui/furnace.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityIronFurnaceBase te;
    private final String name;
	
	public GuiIronFurance(InventoryPlayer invPlayer, TileEntityIronFurnaceBase te, String name) {
		super(new ContainerIronFurance(invPlayer, te));
		this.te = te;
		this.playerInventory = invPlayer;
		this.name = name;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	/**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String actualName = te.getName() != null ? te.getName() : this.name;
        this.fontRenderer.drawString(actualName, this.xSize / 2 - this.fontRenderer.getStringWidth(actualName) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
	
	/**
     * Draws the background layer of this container (behind the items).
     */
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileEntityFurnace.isBurning(this.te))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.te.getField(2);
        int j = this.te.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.te.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.te.getField(0) * pixels / i;
    }

}
