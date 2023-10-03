package ironfurnaces.capability;

import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import ironfurnaces.tileentity.furnaces.BlockSilverFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerFurnacesList implements IPlayerFurnacesList {

    public List<BlockPos> listFurances;

    public PlayerFurnacesList()
    {
        listFurances = new ArrayList<>();
    }


    @Override
    public List<BlockPos> get() {
        return listFurances;
    }

    @Override
    public void add(BlockPos pos) {
        int check = 0;
        for (int i = 0; i < listFurances.size(); i++)
        {
            if (listFurances.get(i).getX() == pos.getX() && listFurances.get(i).getY() == pos.getY() && listFurances.get(i).getZ() == pos.getZ())
            {
                check++;
            }
        }
        if (check == 0)
        {
            listFurances.add(pos);
        }
    }

    @Override
    public void remove(BlockPos pos) {
        for (int i = 0; i < listFurances.size(); i++)
        {
            if (listFurances.get(i).getX() == pos.getX() && listFurances.get(i).getY() == pos.getY() && listFurances.get(i).getZ() == pos.getZ())
            {
                listFurances.remove(i);
            }
        }
    }
}
