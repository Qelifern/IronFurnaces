package ironfurnaces.gui.furnaces;

import ironfurnaces.container.furnaces.BlockEmeraldFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEmeraldFurnaceScreen extends BlockIronFurnaceScreenBase<BlockEmeraldFurnaceContainer> {


    public BlockEmeraldFurnaceScreen(BlockEmeraldFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);

    }

}
