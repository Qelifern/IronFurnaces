package ironfurnaces.jei;


import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import ironfurnaces.gui.*;
import ironfurnaces.init.Registration;
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
		return new ResourceLocation(IronFurnaces.MOD_ID, "plugin_" + IronFurnaces.MOD_ID);
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
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);

			registry.addRecipeCatalyst(new ItemStack(Registration.BLASTING_AUGMENT.get()), VanillaRecipeCategoryUid.BLASTING);
			registry.addRecipeCatalyst(new ItemStack(Registration.SMOKING_AUGMENT.get()), VanillaRecipeCategoryUid.SMOKING);

			registry.addRecipeCatalyst(new ItemStack(Registration.IRON_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.GOLD_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.DIAMOND_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.EMERALD_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.OBSIDIAN_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.COPPER_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeCatalyst(new ItemStack(Registration.SILVER_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		if (Config.enableJeiPlugin.get() && Config.enableJeiClickArea.get()) {
			registry.addRecipeClickArea(BlockIronFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockGoldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockDiamondFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockEmeraldFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockCrystalFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockObsidianFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockCopperFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
			registry.addRecipeClickArea(BlockSilverFurnaceScreen.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
		}
	}

}
