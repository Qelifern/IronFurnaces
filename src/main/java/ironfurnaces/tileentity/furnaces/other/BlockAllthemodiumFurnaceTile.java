package ironfurnaces.tileentity.furnaces.other;

import ironfurnaces.Config;
import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlockAllthemodiumFurnaceTile extends BlockIronFurnaceTileBase {
    public BlockAllthemodiumFurnaceTile(BlockPos pos, BlockState state) {
        super(Registration.ALLTHEMODIUM_FURNACE_TILE.get(), pos, state);
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.allthemodiumFurnaceSpeed;
    }



    @Override
    public String IgetName() {
        return "container.ironfurnaces.allthemodium_furnace";
    }


    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BlockAllthemodiumFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity);
    }


    @Override
    public int getTier() {
        return Config.allthemodiumFurnaceTier.get();
    }

}
