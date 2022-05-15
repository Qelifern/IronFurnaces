package ironfurnaces.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerShowConfigProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public PlayerShowConfig config = new PlayerShowConfig(0);
    private LazyOptional<PlayerShowConfig> lazyConfig = LazyOptional.of(() -> config);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityPlayerShowConfig.CONFIG ? lazyConfig.cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == CapabilityPlayerShowConfig.CONFIG ? lazyConfig.cast() : LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("show", config.value);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        config.value = nbt.getInt("show");
    }
}
