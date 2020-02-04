package ironfurnaces.jei;


import ironfurnaces.Main;
import ironfurnaces.config.Config;
import ironfurnaces.gui.*;
import ironfurnaces.init.ModBlocks;
import ironfurnaces.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class IronFurnacesJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Main.MOD_ID, "plugin_ironfurnaces");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		if (Config.SERVER.enableJeiPlugin.get() && Config.SERVER.enableJeiCatalysts.get()) {
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.iron_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.gold_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.diamond_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.emerald_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.obsidian_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.copper_furnace), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.silver_furnace), VanillaRecipeCategoryUid.FURNACE);

			registry.addRecipeCatalyst(new ItemStack(ModItems.augment_blasting), VanillaRecipeCategoryUid.BLASTING);
			registry.addRecipeCatalyst(new ItemStack(ModItems.augment_smoking), VanillaRecipeCategoryUid.SMOKING);

			registry.addRecipeCatalyst(new ItemStack(ModBlocks.iron_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.gold_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.diamond_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.emerald_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.obsidian_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.copper_furnace), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.silver_furnace), VanillaRecipeCategoryUid.FUEL);
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		if (Config.SERVER.enableJeiPlugin.get() && Config.SERVER.enableJeiClickArea.get()) {
			registry.addRecipeClickArea(BlockIronFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockGoldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockDiamondFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockEmeraldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockObsidianFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockCopperFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockSilverFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE);
		}
	}

}
