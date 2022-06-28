package ironfurnaces.jei;

import com.google.common.collect.Lists;
import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import ironfurnaces.init.Registration;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@JeiPlugin
public class IronFurnacesJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(IronFurnaces.MOD_ID, "plugin_" + IronFurnaces.MOD_ID);
	}

	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {

	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		if (Config.enableJeiPlugin.get())
		{
			registration.addRecipeCategories(new RecipeCategoryGeneratorBlasting(registration.getJeiHelpers().getGuiHelper()));
			registration.addRecipeCategories(new RecipeCategoryGeneratorSmoking(registration.getJeiHelpers().getGuiHelper()));
			registration.addRecipeCategories(new RecipeCategoryGeneratorRegular(registration.getJeiHelpers().getGuiHelper()));

		}
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (Config.enableJeiPlugin.get())
		{

			List<SimpleGeneratorRecipe> recipes = Lists.newArrayList();
			for (Item item : ForgeRegistries.ITEMS.getValues())
			{
				if (BlockIronFurnaceTileBase.getBurnTime(new ItemStack(item)) > 0)
				{
					ItemStack stack = new ItemStack(item);
					recipes.add(new SimpleGeneratorRecipe(BlockIronFurnaceTileBase.getBurnTime(new ItemStack(item)) * 20, stack));
				}
			}
			registration.addRecipes(Registration.RecipeTypes.GENERATOR_REGULAR, recipes);

			List<GeneratorRecipe> recipes1 = Lists.newArrayList();
			List<GeneratorRecipe> list = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(Registration.GENERATOR_RECIPE_TYPE.get()).stream().toList();
			for (GeneratorRecipe item : list)
			{
				recipes1.add(item);
			}
			registration.addRecipes(Registration.RecipeTypes.GENERATOR_BLASTING, recipes1);

			List<SimpleGeneratorRecipe> recipes2 = Lists.newArrayList();
			for (Item item : ForgeRegistries.ITEMS.getValues())
			{
				if (item.getFoodProperties() != null)
				{
					if (item.getFoodProperties().getNutrition() > 0)
					{
						ItemStack stack = new ItemStack(item);
						recipes2.add(new SimpleGeneratorRecipe(BlockIronFurnaceTileBase.getSmokingBurn(stack) * 20, stack));
					}
				}
			}
			registration.addRecipes(Registration.RecipeTypes.GENERATOR_SMOKING, recipes2);


		}
	}


	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		if (Config.enableJeiPlugin.get() && Config.enableJeiCatalysts.get()) {
			registry.addRecipeCatalyst(new ItemStack(Registration.BLASTING_AUGMENT.get()), RecipeTypes.BLASTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SMOKING_AUGMENT.get()), RecipeTypes.SMOKING);

			registry.addRecipeCatalyst(new ItemStack(Registration.GENERATOR_AUGMENT.get()), Registration.RecipeTypes.GENERATOR_REGULAR);
			registry.addRecipeCatalyst(new ItemStack(Registration.GENERATOR_AUGMENT.get()), Registration.RecipeTypes.GENERATOR_BLASTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.GENERATOR_AUGMENT.get()), Registration.RecipeTypes.GENERATOR_SMOKING);

			registry.addRecipeCatalyst(new ItemStack(Registration.FACTORY_AUGMENT.get()), RecipeTypes.SMELTING);

			registry.addRecipeCatalyst(new ItemStack(Registration.IRON_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.GOLD_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.DIAMOND_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.EMERALD_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.OBSIDIAN_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.CRYSTAL_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.NETHERITE_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), RecipeTypes.SMELTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), RecipeTypes.SMELTING);

			if (Config.enableRainbowContent.get()) {
				registry.addRecipeCatalyst(new ItemStack(Registration.MILLION_FURNACE.get()), RecipeTypes.SMELTING);
			}

			registry.addRecipeCatalyst(new ItemStack(Registration.IRON_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.GOLD_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.DIAMOND_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.EMERALD_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.OBSIDIAN_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.CRYSTAL_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.NETHERITE_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), RecipeTypes.FUELING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), RecipeTypes.FUELING);

			if (Config.enableRainbowContent.get()) {
				registry.addRecipeCatalyst(new ItemStack(Registration.MILLION_FURNACE.get()), RecipeTypes.FUELING);
			}

			registry.addRecipeCatalyst(new ItemStack(Registration.BLASTING_AUGMENT.get()), Registration.RecipeTypes.GENERATOR_BLASTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SMOKING_AUGMENT.get()), Registration.RecipeTypes.GENERATOR_SMOKING);



			if (ModList.get().isLoaded("allthemodium"))
			{
				registry.addRecipeCatalyst(new ItemStack(Registration.ALLTHEMODIUM_FURNACE.get()), RecipeTypes.SMELTING);
				registry.addRecipeCatalyst(new ItemStack(Registration.VIBRANIUM_FURNACE.get()), RecipeTypes.SMELTING);
				registry.addRecipeCatalyst(new ItemStack(Registration.UNOBTAINIUM_FURNACE.get()), RecipeTypes.SMELTING);
				registry.addRecipeCatalyst(new ItemStack(Registration.ALLTHEMODIUM_FURNACE.get()), RecipeTypes.FUELING);
				registry.addRecipeCatalyst(new ItemStack(Registration.VIBRANIUM_FURNACE.get()), RecipeTypes.FUELING);
				registry.addRecipeCatalyst(new ItemStack(Registration.UNOBTAINIUM_FURNACE.get()), RecipeTypes.FUELING);
			}
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		/**if (Config.enableJeiPlugin.get() && Config.enableJeiClickArea.get()) {
			registry.addRecipeClickArea(BlockIronFurnaceScreen.class, 79, 35, 24, 17, RecipeTypes.FUELING, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockGoldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockDiamondFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockEmeraldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockCrystalFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockObsidianFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockNetheriteFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockCopperFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockSilverFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeClickArea(BlockMillionFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);

			if (ModList.get().isLoaded("allthemodium"))
			{
				registry.addRecipeClickArea(BlockAllthemodiumFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
				registry.addRecipeClickArea(BlockVibraniumFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
				registry.addRecipeClickArea(BlockUnobtainiumFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
			}
		}**/
	}

}




