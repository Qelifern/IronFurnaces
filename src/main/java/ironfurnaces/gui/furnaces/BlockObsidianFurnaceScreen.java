package ironfurnaces.gui.furnaces;

import ironfurnaces.container.furnaces.BlockObsidianFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockObsidianFurnaceScreen extends BlockIronFurnaceScreenBase<BlockObsidianFurnaceContainer> {


    public BlockObsidianFurnaceScreen(BlockObsidianFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);

    }

}
