package ironfurnaces.capability;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.furnaces.BlockSilverFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IPlayerFurnacesList {

    List<BlockPos> get();

    void add(BlockPos pos);

    void remove(BlockPos pos);

}
