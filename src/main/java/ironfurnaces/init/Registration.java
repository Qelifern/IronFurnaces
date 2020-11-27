package ironfurnaces.init;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import ironfurnaces.blocks.*;
import ironfurnaces.container.*;
import ironfurnaces.items.*;
import ironfurnaces.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static ironfurnaces.IronFurnaces.MOD_ID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    //private static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MOD_ID);
    //private static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, MOD_ID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        //DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }



    public static final RegistryObject<BlockIronFurnace> IRON_FURNACE = BLOCKS.register(BlockIronFurnace.IRON_FURNACE, BlockIronFurnace::new);
    public static final RegistryObject<Item> IRON_FURNACE_ITEM = ITEMS.register(BlockIronFurnace.IRON_FURNACE, () -> new BlockItem(IRON_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockIronFurnaceTile>> IRON_FURNACE_TILE = TILES.register(BlockIronFurnace.IRON_FURNACE, () -> TileEntityType.Builder.create(BlockIronFurnaceTile::new, IRON_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockIronFurnaceContainer>> IRON_FURNACE_CONTAINER = CONTAINERS.register(BlockIronFurnace.IRON_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockIronFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockGoldFurnace> GOLD_FURNACE = BLOCKS.register(BlockGoldFurnace.GOLD_FURNACE, BlockGoldFurnace::new);
    public static final RegistryObject<Item> GOLD_FURNACE_ITEM = ITEMS.register(BlockGoldFurnace.GOLD_FURNACE, () -> new BlockItem(GOLD_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockGoldFurnaceTile>> GOLD_FURNACE_TILE = TILES.register(BlockGoldFurnace.GOLD_FURNACE, () -> TileEntityType.Builder.create(BlockGoldFurnaceTile::new, GOLD_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockGoldFurnaceContainer>> GOLD_FURNACE_CONTAINER = CONTAINERS.register(BlockGoldFurnace.GOLD_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockGoldFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockDiamondFurnace> DIAMOND_FURNACE = BLOCKS.register(BlockDiamondFurnace.DIAMOND_FURNACE, BlockDiamondFurnace::new);
    public static final RegistryObject<Item> DIAMOND_FURNACE_ITEM = ITEMS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> new BlockItem(DIAMOND_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockDiamondFurnaceTile>> DIAMOND_FURNACE_TILE = TILES.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> TileEntityType.Builder.create(BlockDiamondFurnaceTile::new, DIAMOND_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockDiamondFurnaceContainer>> DIAMOND_FURNACE_CONTAINER = CONTAINERS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockDiamondFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockEmeraldFurnace> EMERALD_FURNACE = BLOCKS.register(BlockEmeraldFurnace.EMERALD_FURNACE, BlockEmeraldFurnace::new);
    public static final RegistryObject<Item> EMERALD_FURNACE_ITEM = ITEMS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> new BlockItem(EMERALD_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockEmeraldFurnaceTile>> EMERALD_FURNACE_TILE = TILES.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> TileEntityType.Builder.create(BlockEmeraldFurnaceTile::new, EMERALD_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockEmeraldFurnaceContainer>> EMERALD_FURNACE_CONTAINER = CONTAINERS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockEmeraldFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockObsidianFurnace> OBSIDIAN_FURNACE = BLOCKS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, BlockObsidianFurnace::new);
    public static final RegistryObject<Item> OBSIDIAN_FURNACE_ITEM = ITEMS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> new BlockItem(OBSIDIAN_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockObsidianFurnaceTile>> OBSIDIAN_FURNACE_TILE = TILES.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> TileEntityType.Builder.create(BlockObsidianFurnaceTile::new, OBSIDIAN_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockObsidianFurnaceContainer>> OBSIDIAN_FURNACE_CONTAINER = CONTAINERS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockObsidianFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockCrystalFurnace> CRYSTAL_FURNACE = BLOCKS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, BlockCrystalFurnace::new);
    public static final RegistryObject<Item> CRYSTAL_FURNACE_ITEM = ITEMS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> new BlockItem(CRYSTAL_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockCrystalFurnaceTile>> CRYSTAL_FURNACE_TILE = TILES.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> TileEntityType.Builder.create(BlockCrystalFurnaceTile::new, CRYSTAL_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockCrystalFurnaceContainer>> CRYSTAL_FURNACE_CONTAINER = CONTAINERS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockCrystalFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockNetheriteFurnace> NETHERITE_FURNACE = BLOCKS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, BlockNetheriteFurnace::new);
    public static final RegistryObject<Item> NETHERITE_FURNACE_ITEM = ITEMS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> new BlockItem(NETHERITE_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockNetheriteFurnaceTile>> NETHERITE_FURNACE_TILE = TILES.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> TileEntityType.Builder.create(BlockNetheriteFurnaceTile::new, NETHERITE_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockNetheriteFurnaceContainer>> NETHERITE_FURNACE_CONTAINER = CONTAINERS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockNetheriteFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockCopperFurnace> COPPER_FURNACE = BLOCKS.register(BlockCopperFurnace.COPPER_FURNACE, BlockCopperFurnace::new);
    public static final RegistryObject<Item> COPPER_FURNACE_ITEM = ITEMS.register(BlockCopperFurnace.COPPER_FURNACE, () -> new BlockItem(COPPER_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockCopperFurnaceTile>> COPPER_FURNACE_TILE = TILES.register(BlockCopperFurnace.COPPER_FURNACE, () -> TileEntityType.Builder.create(BlockCopperFurnaceTile::new, COPPER_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockCopperFurnaceContainer>> COPPER_FURNACE_CONTAINER = CONTAINERS.register(BlockCopperFurnace.COPPER_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockCopperFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockSilverFurnace> SILVER_FURNACE = BLOCKS.register(BlockSilverFurnace.SILVER_FURNACE, BlockSilverFurnace::new);
    public static final RegistryObject<Item> SILVER_FURNACE_ITEM = ITEMS.register(BlockSilverFurnace.SILVER_FURNACE, () -> new BlockItem(SILVER_FURNACE.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockSilverFurnaceTile>> SILVER_FURNACE_TILE = TILES.register(BlockSilverFurnace.SILVER_FURNACE, () -> TileEntityType.Builder.create(BlockSilverFurnaceTile::new, SILVER_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockSilverFurnaceContainer>> SILVER_FURNACE_CONTAINER = CONTAINERS.register(BlockSilverFurnace.SILVER_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockSilverFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));


    public static RegistryObject<BlockAllthemodiumFurnace> ALLTHEMODIUM_FURNACE = BLOCKS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, BlockAllthemodiumFurnace::new);
    public static final RegistryObject<Item> ALLTHEMODIUM_FURNACE_ITEM = ITEMS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> new BlockItem(ALLTHEMODIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties().group(ModSetup.ITEM_GROUP) : new Item.Properties()));
    public static final RegistryObject<TileEntityType<BlockAllthemodiumFurnaceTile>> ALLTHEMODIUM_FURNACE_TILE = TILES.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> TileEntityType.Builder.create(BlockAllthemodiumFurnaceTile::new, ALLTHEMODIUM_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockAllthemodiumFurnaceContainer>> ALLTHEMODIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockAllthemodiumFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));


    public static final RegistryObject<BlockVibraniumFurnace> VIBRANIUM_FURNACE = BLOCKS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, BlockVibraniumFurnace::new);
    public static final RegistryObject<Item> VIBRANIUM_FURNACE_ITEM = ITEMS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> new BlockItem(VIBRANIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties().group(ModSetup.ITEM_GROUP) : new Item.Properties()));
    public static final RegistryObject<TileEntityType<BlockVibraniumFurnaceTile>> VIBRANIUM_FURNACE_TILE = TILES.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> TileEntityType.Builder.create(BlockVibraniumFurnaceTile::new, VIBRANIUM_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockVibraniumFurnaceContainer>> VIBRANIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockVibraniumFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockUnobtaniumFurnace> UNOBTANIUM_FURNACE = BLOCKS.register(BlockUnobtaniumFurnace.UNOBTANIUM_FURNACE, BlockUnobtaniumFurnace::new);
    public static final RegistryObject<Item> UNOBTANIUM_FURNACE_ITEM = ITEMS.register(BlockUnobtaniumFurnace.UNOBTANIUM_FURNACE, () -> new BlockItem(UNOBTANIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties().group(ModSetup.ITEM_GROUP) : new Item.Properties()));
    public static final RegistryObject<TileEntityType<BlockUnobtaniumFurnaceTile>> UNOBTANIUM_FURNACE_TILE = TILES.register(BlockUnobtaniumFurnace.UNOBTANIUM_FURNACE, () -> TileEntityType.Builder.create(BlockUnobtaniumFurnaceTile::new, UNOBTANIUM_FURNACE.get()).build(null));

    public static final RegistryObject<ContainerType<BlockUnobtaniumFurnaceContainer>> UNOBTANIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockUnobtaniumFurnace.UNOBTANIUM_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockUnobtaniumFurnaceContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<BlockWirelessEnergyHeater> HEATER = BLOCKS.register(BlockWirelessEnergyHeater.HEATER, BlockWirelessEnergyHeater::new);
    public static final RegistryObject<Item> HEATER_ITEM = ITEMS.register(BlockWirelessEnergyHeater.HEATER, () -> new BlockItem(HEATER.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<BlockWirelessEnergyHeaterTile>> HEATER_TILE = TILES.register(BlockWirelessEnergyHeater.HEATER, () -> TileEntityType.Builder.create(BlockWirelessEnergyHeaterTile::new, HEATER.get()).build(null));

    public static final RegistryObject<ContainerType<BlockWirelessEnergyHeaterContainer>> HEATER_CONTAINER = CONTAINERS.register(BlockWirelessEnergyHeater.HEATER, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new BlockWirelessEnergyHeaterContainer(windowId, IronFurnaces.proxy.getClientWorld(), pos, inv, IronFurnaces.proxy.getClientPlayer());
    }));

    public static final RegistryObject<ItemUpgradeIron> IRON_UPGRADE = ITEMS.register("upgrade_iron", ItemUpgradeIron::new);
    public static final RegistryObject<ItemUpgradeGold> GOLD_UPGRADE = ITEMS.register("upgrade_gold", ItemUpgradeGold::new);
    public static final RegistryObject<ItemUpgradeDiamond> DIAMOND_UPGRADE = ITEMS.register("upgrade_diamond", ItemUpgradeDiamond::new);
    public static final RegistryObject<ItemUpgradeEmerald> EMERALD_UPGRADE = ITEMS.register("upgrade_emerald", ItemUpgradeEmerald::new);
    public static final RegistryObject<ItemUpgradeObsidian> OBSIDIAN_UPGRADE = ITEMS.register("upgrade_obsidian", ItemUpgradeObsidian::new);
    public static final RegistryObject<ItemUpgradeCrystal> CRYSTAL_UPGRADE = ITEMS.register("upgrade_crystal", ItemUpgradeCrystal::new);
    public static final RegistryObject<ItemUpgradeObsidian2> OBSIDIAN2_UPGRADE = ITEMS.register("upgrade_obsidian2", ItemUpgradeObsidian2::new);
    public static final RegistryObject<ItemUpgradeNetherite> NETHERITE_UPGRADE = ITEMS.register("upgrade_netherite", ItemUpgradeNetherite::new);

    public static final RegistryObject<ItemAugmentBlasting> BLASTING_AUGMENT = ITEMS.register("augment_blasting", ItemAugmentBlasting::new);
    public static final RegistryObject<ItemAugmentSmoking> SMOKING_AUGMENT = ITEMS.register("augment_smoking", ItemAugmentSmoking::new);
    public static final RegistryObject<ItemAugmentSpeed> SPEED_AUGMENT = ITEMS.register("augment_speed", ItemAugmentSpeed::new);
    public static final RegistryObject<ItemAugmentFuel> FUEL_AUGMENT = ITEMS.register("augment_fuel", ItemAugmentFuel::new);

    public static final RegistryObject<ItemHeater> ITEM_HEATER = ITEMS.register("item_heater", ItemHeater::new);

    public static final RegistryObject<ItemSpooky> ITEM_SPOOKY = ITEMS.register("item_spooky", ItemSpooky::new);
    public static final RegistryObject<ItemXmas> ITEM_XMAS = ITEMS.register("item_xmas", ItemXmas::new);


}
