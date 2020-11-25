package ironfurnaces.gui;

import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockAllthemodiumFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockAllthemodiumFurnaceScreen extends BlockIronFurnaceScreenBase<BlockAllthemodiumFurnaceContainer> {


    public BlockAllthemodiumFurnaceScreen(BlockAllthemodiumFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" +"textures/gui/furnace_allthemodium.png");
    }
}
