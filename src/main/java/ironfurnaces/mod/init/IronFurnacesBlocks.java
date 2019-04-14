package ironfurnaces.mod.init;

import ironfurnaces.mod.IronFurnaces;
import ironfurnaces.mod.block.*;
import ironfurnaces.mod.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class IronFurnacesBlocks {

	@GameRegistry.ObjectHolder("ironfurnaces:iron_furnace")
	public static Block iron_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:gold_furnace")
	public static Block gold_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:diamond_furnace")
	public static Block diamond_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:emerald_furnace")
	public static Block emerald_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:obsidian_furnace")
	public static Block obsidian_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:copper_furnace")
	public static Block copper_furnace;

	@GameRegistry.ObjectHolder("ironfurnaces:silver_furnace")
	public static Block silver_furnace;
	
	public static final void register(IForgeRegistry<Block> registry) {
		registry.register(new IronFurnace("iron_furnace", "iron_furnace"));
		registry.register(new GoldFurnace("gold_furnace", "gold_furnace"));
		registry.register(new DiamondFurnace("diamond_furnace", "diamond_furnace"));
		registry.register(new EmeraldFurnace("emerald_furnace", "emerald_furnace"));
		registry.register(new ObsidianFurnace("obsidian_furnace", "obsidian_furnace"));
		registry.register(new CopperFurnace("copper_furnace", "copper_furnace"));
		registry.register(new SilverFurnace("silver_furnace", "silver_furnace"));
		GameRegistry.registerTileEntity(TileEntityIronFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityIronFurnace"));
		GameRegistry.registerTileEntity(TileEntityGoldFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityGoldFurnace"));
		GameRegistry.registerTileEntity(TileEntityDiamondFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityDiamondFurnace"));
		GameRegistry.registerTileEntity(TileEntityEmeraldFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityEmeraldFurnace"));
		GameRegistry.registerTileEntity(TileEntityObsidianFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityObsidianFurnace"));
		GameRegistry.registerTileEntity(TileEntityCopperFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntityCopperFurnace"));
		GameRegistry.registerTileEntity(TileEntitySilverFurnace.class, new ResourceLocation(IronFurnaces.MOD_ID, "TileEntitySilverFurnace"));
	}

	public static final void registerRenders() {
		registerRender(iron_furnace);
		registerRender(gold_furnace);
		registerRender(diamond_furnace);
		registerRender(emerald_furnace);
		registerRender(obsidian_furnace);
		registerRender(copper_furnace);
		registerRender(silver_furnace);
	}
	
	public static final void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
				new ModelResourceLocation(item.getRegistryName(), "inventory"));

	}
	
	
}
