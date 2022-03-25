package ironfurnaces.jei;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.*;
import ironfurnaces.init.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

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
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		if (Config.enableJeiPlugin.get() && Config.enableJeiCatalysts.get()) {
			registry.addRecipeCatalyst(new ItemStack(Registration.IRON_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.GOLD_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.DIAMOND_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.EMERALD_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.OBSIDIAN_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.NETHERITE_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);

			if (Config.enableRainbowContent.get()) {
				registry.addRecipeCatalyst(new ItemStack(Registration.MILLION_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			}

			registry.addRecipeCatalyst(new ItemStack(Registration.IRON_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.GOLD_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.DIAMOND_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.EMERALD_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.OBSIDIAN_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.NETHERITE_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);

			if (Config.enableRainbowContent.get()) {
				registry.addRecipeCatalyst(new ItemStack(Registration.MILLION_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			}

			registry.addRecipeCatalyst(new ItemStack(Registration.BLASTING_AUGMENT.get()), VanillaRecipeCategoryUid.BLASTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SMOKING_AUGMENT.get()), VanillaRecipeCategoryUid.SMOKING);



			if (ModList.get().isLoaded("allthemodium"))
			{
				registry.addRecipeCatalyst(new ItemStack(Registration.ALLTHEMODIUM_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
				registry.addRecipeCatalyst(new ItemStack(Registration.VIBRANIUM_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
				registry.addRecipeCatalyst(new ItemStack(Registration.UNOBTAINIUM_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
				registry.addRecipeCatalyst(new ItemStack(Registration.ALLTHEMODIUM_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
				registry.addRecipeCatalyst(new ItemStack(Registration.VIBRANIUM_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
				registry.addRecipeCatalyst(new ItemStack(Registration.UNOBTAINIUM_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			}
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		if (Config.enableJeiPlugin.get() && Config.enableJeiClickArea.get()) {
			registry.addRecipeClickArea(BlockIronFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
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
		}
	}

}




