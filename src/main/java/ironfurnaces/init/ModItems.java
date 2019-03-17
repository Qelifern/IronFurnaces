package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.BlockDiamondFurnace;
import ironfurnaces.blocks.BlockGoldFurnace;
import ironfurnaces.blocks.BlockIronFurnace;
import ironfurnaces.blocks.BlockObsidianFurnace;
import ironfurnaces.items.ItemBlockDiamondFurnace;
import ironfurnaces.items.ItemBlockGoldFurnace;
import ironfurnaces.items.ItemBlockIronFurnace;
import ironfurnaces.items.ItemBlockObsidianFurnace;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlockIronFurnace(ModBlocks.iron_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockIronFurnace.IRON_FURNACE));
        registry.register(new ItemBlockGoldFurnace(ModBlocks.gold_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockGoldFurnace.GOLD_FURNACE));
        registry.register(new ItemBlockDiamondFurnace(ModBlocks.diamond_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockDiamondFurnace.DIAMOND_FURNACE));
        registry.register(new ItemBlockObsidianFurnace(ModBlocks.obsidian_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockObsidianFurnace.OBSIDIAN_FURNACE));
        Main.LOGGER.info("IronFurnaces items Registry Done.");
    }

}
