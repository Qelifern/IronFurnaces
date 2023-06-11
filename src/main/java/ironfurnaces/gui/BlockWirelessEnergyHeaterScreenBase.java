package ironfurnaces.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.util.StringHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public abstract class BlockWirelessEnergyHeaterScreenBase<T extends BlockWirelessEnergyHeaterContainer> extends AbstractContainerScreen<T> {

    public ResourceLocation GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" + "textures/gui/heater.png");
    Inventory playerInv;
    Component name;

    public BlockWirelessEnergyHeaterScreenBase(T t, Inventory inv, Component name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }

    @Override
    public void render(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY) {
        matrix.drawString(font, this.playerInv.getDisplayName(), 7, this.getYSize() - 93, 4210752, false);
        matrix.drawString(font, name, this.getXSize() / 2 - this.minecraft.font.width(name.getString()) / 2, 6, 4210752, false);

        int actualMouseX = mouseX - ((this.width - this.getXSize()) / 2);
        int actualMouseY = mouseY - ((this.height - this.getYSize()) / 2);
        if(actualMouseX >= 68 && actualMouseX <= 108 && actualMouseY >= 64 && actualMouseY <= 76) {
            int energy = ((BlockWirelessEnergyHeaterContainer)this.getMenu()).getEnergy();
            int capacity = ((BlockWirelessEnergyHeaterContainer)this.getMenu()).getMaxEnergy();
            matrix.renderTooltip(this.font, Component.literal(StringHelper.displayEnergy(energy, capacity).get(0)), actualMouseX, actualMouseY);

        }

    }

    @Override
    protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        matrix.blit(GUI, relX, relY, 0, 0, this.getXSize(), this.getYSize());

        int i;
        i = ((BlockWirelessEnergyHeaterContainer)this.getMenu()).getEnergyScaled(42);
        matrix.blit(GUI, getGuiLeft() + 67, getGuiTop() + 63, 176, 0, i + 1, 14);

    }

}
