package ironfurnaces.gui;

import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockWirelessEnergyHeaterScreen extends BlockWirelessEnergyHeaterScreenBase<BlockWirelessEnergyHeaterContainer> {


    public BlockWirelessEnergyHeaterScreen(BlockWirelessEnergyHeaterContainer blockWirelessEnergyHeaterContainer, PlayerInventory inv, ITextComponent name) {
        super(blockWirelessEnergyHeaterContainer, inv, name);
    }
}
