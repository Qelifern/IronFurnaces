package ironfurnaces.gui;

import ironfurnaces.IronFurnaces;
import ironfurnaces.container.BlockNetheriteFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockNetheriteFurnaceScreen extends BlockIronFurnaceScreenBase<BlockNetheriteFurnaceContainer> {


    public BlockNetheriteFurnaceScreen(BlockNetheriteFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.GUI = new ResourceLocation(IronFurnaces.MOD_ID + ":" +"textures/gui/furnace_netherite.png");
    }
}
