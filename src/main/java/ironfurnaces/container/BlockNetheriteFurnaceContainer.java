package ironfurnaces.container;

import ironfurnaces.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetheriteFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockNetheriteFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(Registration.NETHERITE_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }

    public BlockNetheriteFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray fields) {
        super(Registration.NETHERITE_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
    }


    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(te.getLevel(), te.getBlockPos()), playerEntity, Registration.NETHERITE_FURNACE.get());
    }
}
