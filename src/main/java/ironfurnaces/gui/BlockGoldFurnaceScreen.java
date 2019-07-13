package ironfurnaces.gui;

import ironfurnaces.container.BlockGoldFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockGoldFurnaceScreen extends BlockIronFurnaceScreenBase<BlockGoldFurnaceContainer> {


    public BlockGoldFurnaceScreen(BlockGoldFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }
}
