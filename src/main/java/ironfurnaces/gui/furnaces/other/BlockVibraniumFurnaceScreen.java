package ironfurnaces.gui.furnaces.other;

import com.mojang.blaze3d.systems.RenderSystem;
import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockVibraniumFurnaceScreen extends BlockIronFurnaceScreenBase<BlockVibraniumFurnaceContainer> {


    public BlockVibraniumFurnaceScreen(BlockVibraniumFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        GUI = GUI_VIB;
    }
}
