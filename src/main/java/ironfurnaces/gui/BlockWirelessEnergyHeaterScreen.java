package ironfurnaces.gui;

import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockWirelessEnergyHeaterScreen extends BlockWirelessEnergyHeaterScreenBase<BlockWirelessEnergyHeaterContainer> {


    public BlockWirelessEnergyHeaterScreen(BlockWirelessEnergyHeaterContainer blockWirelessEnergyHeaterContainer, Inventory inv, Component name) {
        super(blockWirelessEnergyHeaterContainer, inv, name);
    }
}
