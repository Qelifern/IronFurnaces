package ironfurnaces.gui;

import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockNetheriteFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockNetheriteFurnaceScreen extends BlockIronFurnaceScreenBase<BlockNetheriteFurnaceContainer> {


    public BlockNetheriteFurnaceScreen(BlockNetheriteFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" +"textures/gui/furnace_netherite.png");
    }
}
