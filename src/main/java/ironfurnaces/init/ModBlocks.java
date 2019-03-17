package ironfurnaces.init;

import ironfurnaces.Main;
import ironfurnaces.blocks.BlockDiamondFurnace;
import ironfurnaces.blocks.BlockGoldFurnace;
import ironfurnaces.blocks.BlockIronFurnace;
import ironfurnaces.blocks.BlockObsidianFurnace;
import ironfurnaces.tileentity.TileEntityDiamondFurnace;
import ironfurnaces.tileentity.TileEntityGoldFurnace;
import ironfurnaces.tileentity.TileEntityIronFurnace;
import ironfurnaces.tileentity.TileEntityObsidianFurnace;
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
    public static TileEntityType<?> OBSIDIAN_FURNACE;


    @ObjectHolder("ironfurnaces:iron_furnace")
    public static BlockIronFurnace iron_furnace;

    @ObjectHolder("ironfurnaces:gold_furnace")
    public static BlockGoldFurnace gold_furnace;

    @ObjectHolder("ironfurnaces:diamond_furnace")
    public static BlockDiamondFurnace diamond_furnace;

    @ObjectHolder("ironfurnaces:obsidian_furnace")
    public static BlockObsidianFurnace obsidian_furnace;


    public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry) {
        registry.register(IRON_FURNACE = TileEntityType.Builder.create(TileEntityIronFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "iron_furnace")));
        registry.register(GOLD_FURNACE = TileEntityType.Builder.create(TileEntityGoldFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "gold_furnace")));
        registry.register(DIAMOND_FURNACE = TileEntityType.Builder.create(TileEntityDiamondFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "diamond_furnace")));
        registry.register(OBSIDIAN_FURNACE = TileEntityType.Builder.create(TileEntityObsidianFurnace::new).build(null).setRegistryName(new ResourceLocation(Main.MOD_ID, "obsidian_furnace")));
        Main.LOGGER.info("IronFurnaces TileEntities Registry Done.");

    }

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockIronFurnace(Block.Properties.from(Blocks.IRON_BLOCK)));
        registry.register(new BlockGoldFurnace(Block.Properties.from(Blocks.GOLD_BLOCK)));
        registry.register(new BlockDiamondFurnace(Block.Properties.from(Blocks.DIAMOND_BLOCK)));
        registry.register(new BlockObsidianFurnace(Block.Properties.from(Blocks.OBSIDIAN)));
        Main.LOGGER.info("IronFurnaces Blocks Registry Done.");
    }

}
