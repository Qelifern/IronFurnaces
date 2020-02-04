package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.*;
import ironfurnaces.container.*;
import ironfurnaces.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("ironfurnaces:iron_furnace")
    public static BlockIronFurnace iron_furnace;

    @ObjectHolder("ironfurnaces:iron_furnace")
    public static TileEntityType<BlockIronFurnaceTile> IRON_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:iron_furnace")
    public static ContainerType<BlockIronFurnaceContainer> IRON_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:gold_furnace")
    public static BlockGoldFurnace gold_furnace;

    @ObjectHolder("ironfurnaces:gold_furnace")
    public static TileEntityType<BlockGoldFurnaceTile> GOLD_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:gold_furnace")
    public static ContainerType<BlockGoldFurnaceContainer> GOLD_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:diamond_furnace")
    public static BlockDiamondFurnace diamond_furnace;

    @ObjectHolder("ironfurnaces:diamond_furnace")
    public static TileEntityType<BlockDiamondFurnaceTile> DIAMOND_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:diamond_furnace")
    public static ContainerType<BlockDiamondFurnaceContainer> DIAMOND_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:emerald_furnace")
    public static BlockEmeraldFurnace emerald_furnace;

    @ObjectHolder("ironfurnaces:emerald_furnace")
    public static TileEntityType<BlockEmeraldFurnaceTile> EMERALD_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:emerald_furnace")
    public static ContainerType<BlockEmeraldFurnaceContainer> EMERALD_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:obsidian_furnace")
    public static BlockObsidianFurnace obsidian_furnace;

    @ObjectHolder("ironfurnaces:obsidian_furnace")
    public static TileEntityType<BlockObsidianFurnaceTile> OBSIDIAN_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:obsidian_furnace")
    public static ContainerType<BlockObsidianFurnaceContainer> OBSIDIAN_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:copper_furnace")
    public static BlockCopperFurnace copper_furnace;

    @ObjectHolder("ironfurnaces:copper_furnace")
    public static TileEntityType<BlockCopperFurnaceTile> COPPER_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:copper_furnace")
    public static ContainerType<BlockCopperFurnaceContainer> COPPER_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:silver_furnace")
    public static BlockSilverFurnace silver_furnace;

    @ObjectHolder("ironfurnaces:silver_furnace")
    public static TileEntityType<BlockSilverFurnaceTile> SILVER_FURNACE_TYPE;

    @ObjectHolder("ironfurnaces:silver_furnace")
    public static ContainerType<BlockSilverFurnaceContainer> SILVER_FURNACE_CONTAINER;

    @ObjectHolder("ironfurnaces:heater")
    public static BlockWirelessEnergyHeater heater;

    @ObjectHolder("ironfurnaces:heater")
    public static TileEntityType<BlockWirelessEnergyHeaterTile> HEATER_TYPE;

    @ObjectHolder("ironfurnaces:heater")
    public static ContainerType<BlockWirelessEnergyHeaterContainer> HEATER_CONTAINER;

    public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry) {
        registry.register(TileEntityType.Builder.create(BlockIronFurnaceTile::new, ModBlocks.iron_furnace).build(null).setRegistryName("iron_furnace"));
        registry.register(TileEntityType.Builder.create(BlockGoldFurnaceTile::new, ModBlocks.gold_furnace).build(null).setRegistryName("gold_furnace"));
        registry.register(TileEntityType.Builder.create(BlockDiamondFurnaceTile::new, ModBlocks.diamond_furnace).build(null).setRegistryName("diamond_furnace"));
        registry.register(TileEntityType.Builder.create(BlockEmeraldFurnaceTile::new, ModBlocks.emerald_furnace).build(null).setRegistryName("emerald_furnace"));
        registry.register(TileEntityType.Builder.create(BlockObsidianFurnaceTile::new, ModBlocks.obsidian_furnace).build(null).setRegistryName("obsidian_furnace"));
        registry.register(TileEntityType.Builder.create(BlockCopperFurnaceTile::new, ModBlocks.copper_furnace).build(null).setRegistryName("copper_furnace"));
        registry.register(TileEntityType.Builder.create(BlockSilverFurnaceTile::new, ModBlocks.silver_furnace).build(null).setRegistryName("silver_furnace"));
        registry.register(TileEntityType.Builder.create(BlockWirelessEnergyHeaterTile::new, ModBlocks.heater).build(null).setRegistryName("heater"));
        Main.LOGGER.info("IronFurnaces TileEntities Registry Done.");

    }

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockIronFurnace(Block.Properties.from(Blocks.IRON_BLOCK)));
        registry.register(new BlockGoldFurnace(Block.Properties.from(Blocks.GOLD_BLOCK)));
        registry.register(new BlockDiamondFurnace(Block.Properties.from(Blocks.DIAMOND_BLOCK)));
        registry.register(new BlockEmeraldFurnace(Block.Properties.from(Blocks.EMERALD_BLOCK)));
        registry.register(new BlockObsidianFurnace(Block.Properties.from(Blocks.OBSIDIAN)));
        registry.register(new BlockCopperFurnace(Block.Properties.from(Blocks.GOLD_BLOCK)));
        registry.register(new BlockSilverFurnace(Block.Properties.from(Blocks.IRON_BLOCK)));
        registry.register(new BlockWirelessEnergyHeater(Block.Properties.from(Blocks.IRON_BLOCK)));
        Main.LOGGER.info("IronFurnaces Blocks Registry Done.");
    }

    public static void registerContainers(IForgeRegistry<ContainerType<?>> registry) {
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockIronFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("iron_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockGoldFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("gold_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockDiamondFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("diamond_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockEmeraldFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("emerald_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockObsidianFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("obsidian_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockCopperFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("copper_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockSilverFurnaceContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("silver_furnace"));
        registry.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new BlockWirelessEnergyHeaterContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
        }).setRegistryName("heater"));
        Main.LOGGER.info("IronFurnaces Containers Registry Done.");
    }

}
