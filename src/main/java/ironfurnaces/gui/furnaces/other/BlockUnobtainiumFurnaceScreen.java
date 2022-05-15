package ironfurnaces.gui.furnaces.other;

import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockUnobtainiumFurnaceScreen extends BlockIronFurnaceScreenBase<BlockUnobtainiumFurnaceContainer> {


    public BlockUnobtainiumFurnaceScreen(BlockUnobtainiumFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        GUI = GUI_UNOB;
    }

}
