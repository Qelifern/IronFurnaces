package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.BlockDiamondFurnace;
import ironfurnaces.blocks.BlockGoldFurnace;
import ironfurnaces.blocks.BlockIronFurnace;
import ironfurnaces.blocks.BlockObsidianFurnace;
import ironfurnaces.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("ironfurnaces:upgrade_iron")
    public static ItemUpgrade upgrade_iron;

    @ObjectHolder("ironfurnaces:upgrade_gold")
    public static ItemUpgrade upgrade_gold;

    @ObjectHolder("ironfurnaces:upgrade_diamond")
    public static ItemUpgrade upgrade_diamond;

    @ObjectHolder("ironfurnaces:upgrade_obsidian")
    public static ItemUpgrade upgrade_obsidian;

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlockIronFurnace(ModBlocks.iron_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockIronFurnace.IRON_FURNACE));
        registry.register(new ItemBlockGoldFurnace(ModBlocks.gold_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockGoldFurnace.GOLD_FURNACE));
        registry.register(new ItemBlockDiamondFurnace(ModBlocks.diamond_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockDiamondFurnace.DIAMOND_FURNACE));
        registry.register(new ItemBlockObsidianFurnace(ModBlocks.obsidian_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockObsidianFurnace.OBSIDIAN_FURNACE));

        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{1, 0, 0, 0}).setRegistryName("upgrade_iron"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 1, 0, 0}).setRegistryName("upgrade_gold"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 0, 1, 0}).setRegistryName("upgrade_diamond"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 0, 0, 1}).setRegistryName("upgrade_obsidian"));
        Main.LOGGER.info("IronFurnaces items Registry Done.");
    }

}
