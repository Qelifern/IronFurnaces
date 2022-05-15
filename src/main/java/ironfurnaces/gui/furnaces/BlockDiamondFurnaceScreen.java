package ironfurnaces.gui.furnaces;

import ironfurnaces.container.furnaces.BlockDiamondFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockDiamondFurnaceScreen extends BlockIronFurnaceScreenBase<BlockDiamondFurnaceContainer> {


    public BlockDiamondFurnaceScreen(BlockDiamondFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);

    }

}
