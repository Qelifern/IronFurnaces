package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.*;
import ironfurnaces.items.ItemAugment;
import ironfurnaces.items.ItemHeater;
import ironfurnaces.items.ItemUpgrade;
import net.minecraft.item.BlockItem;
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

    @ObjectHolder("ironfurnaces:upgrade_emerald")
    public static ItemUpgrade upgrade_emerald;

    @ObjectHolder("ironfurnaces:upgrade_obsidian")
    public static ItemUpgrade upgrade_obsidian;

    @ObjectHolder("ironfurnaces:heater")
    public static ItemHeater heater;

    @ObjectHolder("ironfurnaces:augment_blasting")
    public static ItemAugment augment_blasting;

    @ObjectHolder("ironfurnaces:augment_smoking")
    public static ItemAugment augment_smoking;

    public static void register(IForgeRegistry<Item> registry) {

        registry.register(new BlockItem(ModBlocks.iron_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockIronFurnace.IRON_FURNACE));
        registry.register(new BlockItem(ModBlocks.gold_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockGoldFurnace.GOLD_FURNACE));
        registry.register(new BlockItem(ModBlocks.diamond_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockDiamondFurnace.DIAMOND_FURNACE));
        registry.register(new BlockItem(ModBlocks.emerald_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockEmeraldFurnace.EMERALD_FURNACE));
        registry.register(new BlockItem(ModBlocks.obsidian_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockObsidianFurnace.OBSIDIAN_FURNACE));
        registry.register(new BlockItem(ModBlocks.copper_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockCopperFurnace.COPPER_FURNACE));
        registry.register(new BlockItem(ModBlocks.silver_furnace, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockSilverFurnace.SILVER_FURNACE));
        registry.register(new BlockItem(ModBlocks.heater, new Item.Properties().group(Main.itemGroup)).setRegistryName(BlockWirelessEnergyHeater.HEATER));

        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{1, 0, 0, 0, 0}).setRegistryName("upgrade_iron"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 1, 0, 0, 0}).setRegistryName("upgrade_gold"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 0, 1, 0, 0}).setRegistryName("upgrade_diamond"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 0, 0, 1, 0}).setRegistryName("upgrade_emerald"));
        registry.register(new ItemUpgrade(new Item.Properties().group(Main.itemGroup), new int[]{0, 0, 0, 0, 1}).setRegistryName("upgrade_obsidian"));

        registry.register(new ItemHeater(new Item.Properties().group(Main.itemGroup).maxStackSize(1)).setRegistryName("item_heater"));

        registry.register(new ItemAugment(new Item.Properties().group(Main.itemGroup).maxStackSize(1), 0).setRegistryName("augment_blasting"));
        registry.register(new ItemAugment(new Item.Properties().group(Main.itemGroup).maxStackSize(1), 1).setRegistryName("augment_smoking"));
        Main.LOGGER.info("IronFurnaces Items Registry Done.");
    }

}
