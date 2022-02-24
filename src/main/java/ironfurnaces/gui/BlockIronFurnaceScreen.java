package ironfurnaces.gui;

import ironfurnaces.container.BlockIronFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockIronFurnaceScreen extends BlockIronFurnaceScreenBase<BlockIronFurnaceContainer> {


    public BlockIronFurnaceScreen(BlockIronFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }
}
