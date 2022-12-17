package ironfurnaces.init;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import ironfurnaces.blocks.BlockWirelessEnergyHeater;
import ironfurnaces.blocks.furnaces.*;
import ironfurnaces.blocks.furnaces.other.BlockAllthemodiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockUnobtainiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockVibraniumFurnace;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.container.furnaces.*;
import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
import ironfurnaces.items.*;
import ironfurnaces.items.augments.*;
import ironfurnaces.items.upgrades.*;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.*;
import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static ironfurnaces.IronFurnaces.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);

    //private static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MOD_ID);
    //private static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, MOD_ID);

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        //ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        //DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final String GENERATOR_ID = "generator_blasting";

    public static final class RecipeTypes {

        public static mezz.jei.api.recipe.RecipeType<GeneratorRecipe> GENERATOR_BLASTING = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_blasting", GeneratorRecipe.class);
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_SMOKING = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_smoking", SimpleGeneratorRecipe.class);
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_REGULAR = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_regular", SimpleGeneratorRecipe.class);
    }
    public static RegistryObject<RecipeType<GeneratorRecipe>> GENERATOR_RECIPE_TYPE = RECIPE_TYPES.register(GENERATOR_ID, () -> new RecipeType<GeneratorRecipe>() {
        @Override
        public String toString() {
            return GENERATOR_ID;
        }
    });
    public static RegistryObject<RecipeSerializer<GeneratorRecipe>> GENERATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(GENERATOR_ID, GeneratorRecipe.Serializer::new);


    public static final RegistryObject<BlockIronFurnace> IRON_FURNACE = BLOCKS.register(BlockIronFurnace.IRON_FURNACE, () -> new BlockIronFurnace(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item> IRON_FURNACE_ITEM = ITEMS.register(BlockIronFurnace.IRON_FURNACE, () -> new ItemFurnace(IRON_FURNACE.get(), new Item.Properties(), Config.ironFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockIronFurnaceTile>> IRON_FURNACE_TILE = TILES.register(BlockIronFurnace.IRON_FURNACE, () -> BlockEntityType.Builder.of(BlockIronFurnaceTile::new, IRON_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockIronFurnaceContainer>> IRON_FURNACE_CONTAINER = CONTAINERS.register(BlockIronFurnace.IRON_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockIronFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));


    public static final RegistryObject<BlockGoldFurnace> GOLD_FURNACE = BLOCKS.register(BlockGoldFurnace.GOLD_FURNACE, () -> new BlockGoldFurnace(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Item> GOLD_FURNACE_ITEM = ITEMS.register(BlockGoldFurnace.GOLD_FURNACE, () -> new ItemFurnace(GOLD_FURNACE.get(), new Item.Properties(), Config.goldFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockGoldFurnaceTile>> GOLD_FURNACE_TILE = TILES.register(BlockGoldFurnace.GOLD_FURNACE, () -> BlockEntityType.Builder.of(BlockGoldFurnaceTile::new, GOLD_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockGoldFurnaceContainer>> GOLD_FURNACE_CONTAINER = CONTAINERS.register(BlockGoldFurnace.GOLD_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockGoldFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockDiamondFurnace> DIAMOND_FURNACE = BLOCKS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> new BlockDiamondFurnace(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Item> DIAMOND_FURNACE_ITEM = ITEMS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> new ItemFurnace(DIAMOND_FURNACE.get(), new Item.Properties(), Config.diamondFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockDiamondFurnaceTile>> DIAMOND_FURNACE_TILE = TILES.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> BlockEntityType.Builder.of(BlockDiamondFurnaceTile::new, DIAMOND_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockDiamondFurnaceContainer>> DIAMOND_FURNACE_CONTAINER = CONTAINERS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockDiamondFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockEmeraldFurnace> EMERALD_FURNACE = BLOCKS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> new BlockEmeraldFurnace(Block.Properties.copy(Blocks.EMERALD_BLOCK)));
    public static final RegistryObject<Item> EMERALD_FURNACE_ITEM = ITEMS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> new ItemFurnace(EMERALD_FURNACE.get(), new Item.Properties(), Config.emeraldFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockEmeraldFurnaceTile>> EMERALD_FURNACE_TILE = TILES.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> BlockEntityType.Builder.of(BlockEmeraldFurnaceTile::new, EMERALD_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockEmeraldFurnaceContainer>> EMERALD_FURNACE_CONTAINER = CONTAINERS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockEmeraldFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockObsidianFurnace> OBSIDIAN_FURNACE = BLOCKS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> new BlockObsidianFurnace(Block.Properties.copy(Blocks.OBSIDIAN).strength(40.0F, 6000.0F)));
    public static final RegistryObject<Item> OBSIDIAN_FURNACE_ITEM = ITEMS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> new ItemFurnace(OBSIDIAN_FURNACE.get(), new Item.Properties(), Config.obsidianFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockObsidianFurnaceTile>> OBSIDIAN_FURNACE_TILE = TILES.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> BlockEntityType.Builder.of(BlockObsidianFurnaceTile::new, OBSIDIAN_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockObsidianFurnaceContainer>> OBSIDIAN_FURNACE_CONTAINER = CONTAINERS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockObsidianFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockCrystalFurnace> CRYSTAL_FURNACE = BLOCKS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> new BlockCrystalFurnace(Block.Properties.copy(Blocks.PRISMARINE).noOcclusion().isValidSpawn(Registration::isntSolid).isSuffocating(Registration::isntSolid).isViewBlocking(Registration::isntSolid)));

    private static Boolean isntSolid(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return (Boolean) false;
    }

    public static final RegistryObject<Item> CRYSTAL_FURNACE_ITEM = ITEMS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> new ItemFurnace(CRYSTAL_FURNACE.get(), new Item.Properties(), Config.crystalFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockCrystalFurnaceTile>> CRYSTAL_FURNACE_TILE = TILES.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> BlockEntityType.Builder.of(BlockCrystalFurnaceTile::new, CRYSTAL_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockCrystalFurnaceContainer>> CRYSTAL_FURNACE_CONTAINER = CONTAINERS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockCrystalFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));



    private static boolean isntSolid(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    public static final RegistryObject<BlockNetheriteFurnace> NETHERITE_FURNACE = BLOCKS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> new BlockNetheriteFurnace(Block.Properties.copy(Blocks.NETHERITE_BLOCK).strength(40.0F, 6000.0F)));
    public static final RegistryObject<Item> NETHERITE_FURNACE_ITEM = ITEMS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> new ItemFurnace(NETHERITE_FURNACE.get(), new Item.Properties(), Config.netheriteFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockNetheriteFurnaceTile>> NETHERITE_FURNACE_TILE = TILES.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> BlockEntityType.Builder.of(BlockNetheriteFurnaceTile::new, NETHERITE_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockNetheriteFurnaceContainer>> NETHERITE_FURNACE_CONTAINER = CONTAINERS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockNetheriteFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockCopperFurnace> COPPER_FURNACE = BLOCKS.register(BlockCopperFurnace.COPPER_FURNACE, () -> new BlockCopperFurnace(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Item> COPPER_FURNACE_ITEM = ITEMS.register(BlockCopperFurnace.COPPER_FURNACE, () -> new ItemFurnace(COPPER_FURNACE.get(), new Item.Properties(), Config.copperFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockCopperFurnaceTile>> COPPER_FURNACE_TILE = TILES.register(BlockCopperFurnace.COPPER_FURNACE, () -> BlockEntityType.Builder.of(BlockCopperFurnaceTile::new, COPPER_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockCopperFurnaceContainer>> COPPER_FURNACE_CONTAINER = CONTAINERS.register(BlockCopperFurnace.COPPER_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockCopperFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockSilverFurnace> SILVER_FURNACE = BLOCKS.register(BlockSilverFurnace.SILVER_FURNACE, () -> new BlockSilverFurnace(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item> SILVER_FURNACE_ITEM = ITEMS.register(BlockSilverFurnace.SILVER_FURNACE, () -> new ItemFurnace(SILVER_FURNACE.get(), new Item.Properties(), Config.silverFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockSilverFurnaceTile>> SILVER_FURNACE_TILE = TILES.register(BlockSilverFurnace.SILVER_FURNACE, () -> BlockEntityType.Builder.of(BlockSilverFurnaceTile::new, SILVER_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockSilverFurnaceContainer>> SILVER_FURNACE_CONTAINER = CONTAINERS.register(BlockSilverFurnace.SILVER_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockSilverFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ItemUpgradeIron> IRON_UPGRADE = ITEMS.register("upgrade_iron", () -> new ItemUpgradeIron(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeGold> GOLD_UPGRADE = ITEMS.register("upgrade_gold", () -> new ItemUpgradeGold(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeDiamond> DIAMOND_UPGRADE = ITEMS.register("upgrade_diamond", () -> new ItemUpgradeDiamond(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeEmerald> EMERALD_UPGRADE = ITEMS.register("upgrade_emerald", () -> new ItemUpgradeEmerald(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeObsidian> OBSIDIAN_UPGRADE = ITEMS.register("upgrade_obsidian", () -> new ItemUpgradeObsidian(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeCrystal> CRYSTAL_UPGRADE = ITEMS.register("upgrade_crystal", () -> new ItemUpgradeCrystal(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeNetherite> NETHERITE_UPGRADE = ITEMS.register("upgrade_netherite", () -> new ItemUpgradeNetherite(new Item.Properties()));

    public static final RegistryObject<ItemUpgradeCopper> COPPER_UPGRADE = ITEMS.register("upgrade_copper", () -> new ItemUpgradeCopper(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeSilver> SILVER_UPGRADE = ITEMS.register("upgrade_silver", () -> new ItemUpgradeSilver(new Item.Properties()));

    public static final RegistryObject<ItemUpgradeObsidian2> OBSIDIAN2_UPGRADE = ITEMS.register("upgrade_obsidian2", () -> new ItemUpgradeObsidian2(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeIron2> IRON2_UPGRADE = ITEMS.register("upgrade_iron2", () -> new ItemUpgradeIron2(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeGold2> GOLD2_UPGRADE = ITEMS.register("upgrade_gold2", () -> new ItemUpgradeGold2(new Item.Properties()));
    public static final RegistryObject<ItemUpgradeSilver2> SILVER2_UPGRADE = ITEMS.register("upgrade_silver2", () -> new ItemUpgradeSilver2(new Item.Properties()));

    public static RegistryObject<BlockAllthemodiumFurnace> ALLTHEMODIUM_FURNACE = BLOCKS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> new BlockAllthemodiumFurnace(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Item> ALLTHEMODIUM_FURNACE_ITEM = ITEMS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> new ItemFurnace(ALLTHEMODIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties(), Config.allthemodiumFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockAllthemodiumFurnaceTile>> ALLTHEMODIUM_FURNACE_TILE = TILES.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> BlockEntityType.Builder.of(BlockAllthemodiumFurnaceTile::new, ALLTHEMODIUM_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockAllthemodiumFurnaceContainer>> ALLTHEMODIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockAllthemodiumFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockVibraniumFurnace> VIBRANIUM_FURNACE = BLOCKS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> new BlockVibraniumFurnace(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Item> VIBRANIUM_FURNACE_ITEM = ITEMS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> new ItemFurnace(VIBRANIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties(), Config.vibraniumFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockVibraniumFurnaceTile>> VIBRANIUM_FURNACE_TILE = TILES.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> BlockEntityType.Builder.of(BlockVibraniumFurnaceTile::new, VIBRANIUM_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockVibraniumFurnaceContainer>> VIBRANIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockVibraniumFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<BlockUnobtainiumFurnace> UNOBTAINIUM_FURNACE = BLOCKS.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> new BlockUnobtainiumFurnace(Block.Properties.copy(Blocks.NETHERITE_BLOCK)));
    public static final RegistryObject<Item> UNOBTAINIUM_FURNACE_ITEM = ITEMS.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> new ItemFurnace(UNOBTAINIUM_FURNACE.get(), ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties(), Config.unobtainiumFurnaceSpeed.get()));
    public static final RegistryObject<BlockEntityType<BlockUnobtainiumFurnaceTile>> UNOBTAINIUM_FURNACE_TILE = TILES.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> BlockEntityType.Builder.of(BlockUnobtainiumFurnaceTile::new, UNOBTAINIUM_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockUnobtainiumFurnaceContainer>> UNOBTAINIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockUnobtainiumFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));


    public static final RegistryObject<ItemUpgradeAllthemodium> ALLTHEMODIUM_UPGRADE = ITEMS.register("upgrade_allthemodium", () -> new ItemUpgradeAllthemodium(ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties()));
    public static final RegistryObject<ItemUpgradeVibranium> VIBRANIUM_UPGRADE = ITEMS.register("upgrade_vibranium", () -> new ItemUpgradeVibranium(ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties()));
    public static final RegistryObject<ItemUpgradeUnobtainium> UNOBTAINIUM_UPGRADE = ITEMS.register("upgrade_unobtainium", () -> new ItemUpgradeUnobtainium(ModList.get().isLoaded("allthemodium") ? new Item.Properties() : new Item.Properties()));






    public static final RegistryObject<BlockWirelessEnergyHeater> HEATER = BLOCKS.register(BlockWirelessEnergyHeater.HEATER, () -> new BlockWirelessEnergyHeater(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item> HEATER_ITEM = ITEMS.register(BlockWirelessEnergyHeater.HEATER, () -> new BlockItemHeater(HEATER.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<BlockWirelessEnergyHeaterTile>> HEATER_TILE = TILES.register(BlockWirelessEnergyHeater.HEATER, () -> BlockEntityType.Builder.of(BlockWirelessEnergyHeaterTile::new, HEATER.get()).build(null));

    public static final RegistryObject<MenuType<BlockWirelessEnergyHeaterContainer>> HEATER_CONTAINER = CONTAINERS.register(BlockWirelessEnergyHeater.HEATER, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockWirelessEnergyHeaterContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ItemHeater> ITEM_HEATER = ITEMS.register("item_heater", () -> new ItemHeater(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<ItemAugmentBlasting> BLASTING_AUGMENT = ITEMS.register("augment_blasting", () -> new ItemAugmentBlasting(new Item.Properties()));
    public static final RegistryObject<ItemAugmentSmoking> SMOKING_AUGMENT = ITEMS.register("augment_smoking", () -> new ItemAugmentSmoking(new Item.Properties()));
    public static final RegistryObject<ItemAugmentFactory> FACTORY_AUGMENT = ITEMS.register("augment_factory", () -> new ItemAugmentFactory(new Item.Properties()));
    public static final RegistryObject<ItemAugmentGenerator> GENERATOR_AUGMENT = ITEMS.register("augment_generator", () -> new ItemAugmentGenerator(new Item.Properties()));
    public static final RegistryObject<ItemAugmentSpeed> SPEED_AUGMENT = ITEMS.register("augment_speed", () -> new ItemAugmentSpeed(new Item.Properties()));
    public static final RegistryObject<ItemAugmentFuel> FUEL_AUGMENT = ITEMS.register("augment_fuel", () -> new ItemAugmentFuel(new Item.Properties()));
    public static final RegistryObject<ItemAugmentXP> XP_AUGMENT = ITEMS.register("augment_xp", () -> new ItemAugmentXP(new Item.Properties()));

    public static final RegistryObject<ItemSpooky> ITEM_SPOOKY = ITEMS.register("item_spooky", () -> new ItemSpooky(new Item.Properties()));
    public static final RegistryObject<ItemXmas> ITEM_XMAS = ITEMS.register("item_xmas", () -> new ItemXmas(new Item.Properties()));

    public static final RegistryObject<ItemFurnaceCopy> ITEM_COPY = ITEMS.register("item_copy", () -> new ItemFurnaceCopy(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<ItemLinker> ITEM_LINKER = ITEMS.register("item_linker", () -> new ItemLinker(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RAINBOW_CORE = ITEMS.register("rainbow_core", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_PLATING = ITEMS.register("rainbow_plating", () -> new Item(new Item.Properties()));
    public static final RegistryObject<ItemRainbowCoal> RAINBOW_COAL = ITEMS.register("rainbow_coal", () -> new ItemRainbowCoal(new Item.Properties()));


    public static final RegistryObject<BlockMillionFurnace> MILLION_FURNACE = BLOCKS.register(BlockMillionFurnace.MILLION_FURNACE, () -> new BlockMillionFurnace(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item> MILLION_FURNACE_ITEM = ITEMS.register(BlockMillionFurnace.MILLION_FURNACE, () -> new ItemMillionFurnace(MILLION_FURNACE.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<BlockMillionFurnaceTile>> MILLION_FURNACE_TILE = TILES.register(BlockMillionFurnace.MILLION_FURNACE, () -> BlockEntityType.Builder.of(BlockMillionFurnaceTile::new, MILLION_FURNACE.get()).build(null));

    public static final RegistryObject<MenuType<BlockMillionFurnaceContainer>> MILLION_FURNACE_CONTAINER = CONTAINERS.register(BlockMillionFurnace.MILLION_FURNACE, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BlockMillionFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));






}
