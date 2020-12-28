package ironfurnaces.gui;

import ironfurnaces.container.BlockMillionFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockMillionFurnaceScreen extends BlockIronFurnaceScreenBase<BlockMillionFurnaceContainer> {


    public BlockMillionFurnaceScreen(BlockMillionFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }
}
