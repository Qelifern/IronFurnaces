package ironfurnaces.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFurnacesListProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public PlayerFurnacesList furnacesList = new PlayerFurnacesList();
    private LazyOptional<PlayerFurnacesList> lazyList = LazyOptional.of(() -> furnacesList);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityPlayerFurnacesList.FURNACES_LIST ? lazyList.cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == CapabilityPlayerFurnacesList.FURNACES_LIST ? lazyList.cast() : LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag furnaces = new CompoundTag();
        for (int i = 0; i < furnacesList.listFurances.size(); i++)
        {
            CompoundTag blockpos = new CompoundTag();
            blockpos.putInt("X", furnacesList.listFurances.get(i).getX());
            blockpos.putInt("Y", furnacesList.listFurances.get(i).getY());
            blockpos.putInt("Z", furnacesList.listFurances.get(i).getZ());
            furnaces.put("furnace" + i, blockpos);
        }


        tag.put("furnaces", furnaces);
        tag.putInt("count", furnacesList.listFurances.size());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        int size = tag.getInt("count");
        CompoundTag furances = tag.getCompound("furnaces");
        for (int i = 0; i < size; i++)
        {
            CompoundTag furance = furances.getCompound("furnace" + i);
            BlockPos pos = new BlockPos(furance.getInt("X"), furance.getInt("Y"), furance.getInt("Z"));
            furnacesList.listFurances.add(pos);
        }
    }
}
