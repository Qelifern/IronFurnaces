package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.*;
import ironfurnaces.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    public static TileEntityType<?> IRON_FURNACE;
    public static TileEntityType<?> GOLD_FURNACE;
    public static TileEntityType<?> DIAMOND_FURNACE;
    public static TileEntityType<?> EMERALD_FURNACE;
    public static TileEntityType<?> OBSIDIAN_FURNACE;
    public static TileEntityType<?> COPPER_FURNACE;
    public static TileEntityType<?> SILVER_FURNACE;


    @ObjectHolder("ironfurnaces:iron_furnace")
    public static BlockIronFurnace iron_furnace;

    @ObjectHolder("ironfurnaces:gold_furnace")
    public static BlockGoldFurnace gold_furnace;

    @ObjectHolder("ironfurnaces:diamond_furnace")
    public static BlockDiamondFurnace diamond_furnace;

    @ObjectHolder("ironfurnaces:emerald_furnace")
    public static BlockEmeraldFurnace emerald_furnace;

    @ObjectHolder("ironfurnaces:obsidian_furnace")
    public static BlockObsidianFurnace obsidian_furnace;

    @ObjectHolder("ironfurnaces:copper_furnace")
    public static BlockCopperFurnace copper_furnace;

    @ObjectHolder("ironfurnaces:silver_furnace")
    public static BlockSilverFurnace silver_furnace;

    public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry) {
        registry.register(IRON_FURNACE = TileEntityType.Builder.create(TileEntityIronFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "iron_furnace")));
        registry.register(GOLD_FURNACE = TileEntityType.Builder.create(TileEntityGoldFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "gold_furnace")));
        registry.register(DIAMOND_FURNACE = TileEntityType.Builder.create(TileEntityDiamondFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "diamond_furnace")));
        registry.register(EMERALD_FURNACE = TileEntityType.Builder.create(TileEntityEmeraldFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "emerald_furnace")));
        registry.register(OBSIDIAN_FURNACE = TileEntityType.Builder.create(TileEntityObsidianFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "obsidian_furnace")));
        registry.register(COPPER_FURNACE = TileEntityType.Builder.create(TileEntityCopperFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "copper_furnace")));
        registry.register(SILVER_FURNACE = TileEntityType.Builder.create(TileEntitySilverFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "silver_furnace")));
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
        Main.LOGGER.info("IronFurnaces Blocks Registry Done.");
    }

}
