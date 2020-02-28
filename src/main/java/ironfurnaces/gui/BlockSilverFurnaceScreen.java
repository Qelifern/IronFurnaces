package ironfurnaces.gui;

import ironfurnaces.container.BlockSilverFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockSilverFurnaceScreen extends BlockIronFurnaceScreenBase<BlockSilverFurnaceContainer> {


    public BlockSilverFurnaceScreen(BlockSilverFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }
}
