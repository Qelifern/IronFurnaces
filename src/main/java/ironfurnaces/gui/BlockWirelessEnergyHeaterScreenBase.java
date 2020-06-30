package ironfurnaces.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.util.StringHelper;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BlockWirelessEnergyHeaterScreenBase<T extends BlockWirelessEnergyHeaterContainer> extends ContainerScreen<T> {

    public ResourceLocation GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" + "textures/gui/heater.png");
    PlayerInventory playerInv;
    ITextComponent name;

    public BlockWirelessEnergyHeaterScreenBase(T t, PlayerInventory inv, ITextComponent name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }

    @Override
    public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.func_230446_a_(matrix);
        super.func_230430_a_(matrix, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrix, mouseX, mouseY);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrix, int mouseX, int mouseY) {
        this.field_230706_i_.fontRenderer.func_238422_b_(matrix, this.playerInv.getDisplayName(), 7, this.ySize - 93, 4210752);
        this.field_230706_i_.fontRenderer.func_238422_b_(matrix, name, 7 + this.xSize / 2 - this.field_230706_i_.fontRenderer.func_238414_a_(name) / 2, 6, 4210752);

        int actualMouseX = mouseX - ((this.field_230708_k_ - this.xSize) / 2);
        int actualMouseY = mouseY - ((this.field_230709_l_ - this.ySize) / 2);
        if(actualMouseX >= 65 && actualMouseX <= 111 && actualMouseY >= 64 && actualMouseY <= 76) {
            int energy = ((BlockWirelessEnergyHeaterContainer)this.container).getEnergy();
            int capacity = ((BlockWirelessEnergyHeaterContainer)this.container).getCapacity();
            this.func_238652_a_(matrix, new StringTextComponent(StringHelper.displayEnergy(energy, capacity).get(0)), actualMouseX, actualMouseY);
        }

    }

    @Override
    protected void func_230450_a_(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_230706_i_.getTextureManager().bindTexture(GUI);
        int relX = (this.field_230708_k_ - this.xSize) / 2;
        int relY = (this.field_230709_l_ - this.ySize) / 2;
        this.func_238474_b_(matrix, relX, relY, 0, 0, this.xSize, this.ySize);

        int i;
        i = ((BlockWirelessEnergyHeaterContainer)this.container).getEnergyScaled(46);
        this.func_238474_b_(matrix, guiLeft + 65, guiTop + 64, 176, 0, i + 1, 12);
    }

}
