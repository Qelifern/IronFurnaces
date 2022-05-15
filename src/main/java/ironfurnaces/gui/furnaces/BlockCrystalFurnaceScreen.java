package ironfurnaces.gui.furnaces;

import ironfurnaces.container.furnaces.BlockCrystalFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockCrystalFurnaceScreen extends BlockIronFurnaceScreenBase<BlockCrystalFurnaceContainer> {


    public BlockCrystalFurnaceScreen(BlockCrystalFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);

    }

}
