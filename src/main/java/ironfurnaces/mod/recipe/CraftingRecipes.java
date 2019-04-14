package ironfurnaces.mod.recipe;

import ironfurnaces.mod.block.IronFurnaceBase;
import ironfurnaces.mod.init.IronFurnacesBlocks;
import ironfurnaces.mod.init.IronFurnacesItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftingRecipes {

	public static void recipes() {
		
		IronFurnaceBase.recipe(IronFurnacesBlocks.iron_furnace, Blocks.FURNACE, Items.IRON_INGOT);
		IronFurnaceBase.recipe(IronFurnacesBlocks.copper_furnace, Blocks.FURNACE, "ingotCopper");
		IronFurnaceBase.recipe(IronFurnacesBlocks.silver_furnace, IronFurnacesBlocks.copper_furnace, "ingotSilver");
		IronFurnaceBase.recipe(IronFurnacesBlocks.gold_furnace, IronFurnacesBlocks.iron_furnace, Items.GOLD_INGOT);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.iron_furnace), "IGI", "GFG", "IGI", 'I', Items.IRON_INGOT, 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.copper_furnace);
		IronFurnacesCraftingHelper.addShapedOreRecipe(new ItemStack(IronFurnacesBlocks.silver_furnace), "IGI", "GFG", "IGI", 'I', "ingotSilver", 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.iron_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.gold_furnace), "IGI", "GFG", "IGI", 'I', Items.GOLD_INGOT, 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.silver_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.diamond_furnace), "GGG", "DFD", "GGG", 'D', Items.DIAMOND, 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.gold_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.diamond_furnace), "GGG", "GFG", "DDD", 'D', Items.DIAMOND, 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.silver_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.emerald_furnace), "GGG", "DFD", "GGG", 'D', Items.EMERALD, 'G', Blocks.GLASS, 'F', IronFurnacesBlocks.diamond_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesBlocks.obsidian_furnace), "GDG", "DFD", "GDG", 'D', Items.BLAZE_ROD, 'G', Blocks.OBSIDIAN, 'F', IronFurnacesBlocks.emerald_furnace);
		IronFurnacesCraftingHelper.addShapelessRecipe(new ItemStack(IronFurnacesItems.upgrade_iron), IronFurnacesBlocks.iron_furnace);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesItems.upgrade_gold), "XXX", "XFX", "XXX", 'X', Items.GOLD_INGOT, 'F', IronFurnacesItems.upgrade_iron);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesItems.upgrade_diamond), "GGG", "XFX", "GGG", 'X', Items.DIAMOND, 'F', IronFurnacesItems.upgrade_gold, 'G', Blocks.GLASS);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesItems.upgrade_emerald), "GGG", "XFX", "GGG", 'X', Items.EMERALD, 'F', IronFurnacesItems.upgrade_diamond, 'G', Blocks.GLASS);
		IronFurnacesCraftingHelper.addShapedRecipe(new ItemStack(IronFurnacesItems.upgrade_obsidian), "GXG", "XFX", "GXG", 'X', Items.BLAZE_ROD, 'F', IronFurnacesItems.upgrade_emerald, 'G', Blocks.OBSIDIAN);


	}

	
	
}
