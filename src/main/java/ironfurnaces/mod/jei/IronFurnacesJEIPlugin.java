package ironfurnaces.mod.jei;

import ironfurnaces.mod.gui.GuiIronFurance;
import ironfurnaces.mod.init.IronFurnacesBlocks;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IronFurnacesJEIPlugin implements IModPlugin {

	
	@Override
	public void register(IModRegistry registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		
		
		registry.addRecipeClickArea(GuiIronFurance.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.SMELTING);
		
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.iron_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.gold_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.diamond_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.emerald_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.obsidian_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.copper_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(IronFurnacesBlocks.silver_furnace), VanillaRecipeCategoryUid.SMELTING);
	}
	
	
}
