package ironfurnaces.gui;

import ironfurnaces.container.BlockEmeraldFurnaceContainer;
import ironfurnaces.container.BlockObsidianFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockObsidianFurnaceScreen extends BlockIronFurnaceScreenBase<BlockObsidianFurnaceContainer> {


    public BlockObsidianFurnaceScreen(BlockObsidianFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }
}
