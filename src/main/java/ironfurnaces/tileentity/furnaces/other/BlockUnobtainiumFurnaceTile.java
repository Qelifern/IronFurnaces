package ironfurnaces.tileentity.furnaces.other;

import ironfurnaces.Config;
import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockUnobtainiumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockUnobtainiumFurnaceTile(BlockPos pos, BlockState state) {
        super(Registration.UNOBTAINIUM_FURNACE_TILE.get(), pos, state);
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.unobtainiumFurnaceSpeed;
    }

    @Override
    public String IgetName() {
        return "container.ironfurnaces.unobtainium_furnace";
    }



    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockUnobtainiumFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity);
    }

    @Override
    public int getTier() {
        return Config.unobtainiumFurnaceTier.get();
    }

}
