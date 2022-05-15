package ironfurnaces.tileentity.furnaces;

import com.google.common.collect.Lists;
import ironfurnaces.Config;
import ironfurnaces.container.furnaces.BlockMillionFurnaceContainer;
import ironfurnaces.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class BlockMillionFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockMillionFurnaceTile(BlockPos pos, BlockState state) {
        super(Registration.MILLION_FURNACE_TILE.get(), pos, state);
    }

    public List<BlockIronFurnaceTileBase> furnaces = Lists.newArrayList();
    public List<BlockPos> furnaces_to_load = Lists.newArrayList();

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag furnaces = new CompoundTag();
        for (int i = 0; i < this.furnaces.size(); i++)
        {
            CompoundTag tag2 = new CompoundTag();
            tag2.putInt("X", this.furnaces.get(i).getBlockPos().getX());
            tag2.putInt("Y", this.furnaces.get(i).getBlockPos().getY());
            tag2.putInt("Z", this.furnaces.get(i).getBlockPos().getZ());
            furnaces.put("Furnace" + i, tag2);
        }
        tag.put("Furnaces", furnaces);

    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag furnaces = tag.getCompound("Furnaces");
        for (int i = 0; i < furnaces.size(); i++)
        {
            CompoundTag furnace = furnaces.getCompound("Furnace" + i);
            furnaces_to_load.add(new BlockPos(furnace.getInt("X"), furnace.getInt("Y"), furnace.getInt("Z")));
        }
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.millionFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.million_furnace";
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockMillionFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity);
    }

    @Override
    public int getTier() {
        return Config.millionFurnaceTier.get();
    }


}
