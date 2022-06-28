package ironfurnaces.jei;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import ironfurnaces.IronFurnaces;
import ironfurnaces.init.Registration;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.util.StringHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;

public class RecipeCategoryGeneratorBlasting implements IRecipeCategory<GeneratorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(IronFurnaces.MOD_ID, "category_generator_blasting");
    public IGuiHelper guiHelper;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    protected final IDrawableStatic staticEnergy;
    protected final IDrawableAnimated animatedEnergy;



    public RecipeCategoryGeneratorBlasting(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
        staticFlame = guiHelper.createDrawable(new ResourceLocation(IronFurnaces.MOD_ID, "textures/gui/jei.png"), 68, 0, 14, 14);
        animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        staticEnergy = guiHelper.createDrawable(new ResourceLocation(IronFurnaces.MOD_ID, "textures/gui/jei.png"), 82, 0, 14, 42);
        animatedEnergy = guiHelper.createAnimatedDrawable(staticEnergy, 300, IDrawableAnimated.StartDirection.BOTTOM, false);

    }

    @Override
    public RecipeType<GeneratorRecipe> getRecipeType() {
        return Registration.RecipeTypes.GENERATOR_BLASTING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(IronFurnaces.MOD_ID + ".jei_category_blasting");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(new ResourceLocation(IronFurnaces.MOD_ID, "textures/gui/jei.png"), 0, 0, 68, 42);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.GENERATOR_AUGMENT.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 1, 18)
                .addIngredients(Ingredient.of(recipe.getIngredient().getItem()));
    }

    @Override
    public void draw(GeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
         animatedFlame.draw(stack, 1, 1);
         animatedEnergy.draw(stack, 54, 0);
    }

    @Override
    public List<Component> getTooltipStrings(GeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> list = Lists.newArrayList();
        if (mouseX >= 55 && mouseX <= 68 && mouseY >= 1 && mouseY <= 42)
        {
            list.add(Component.literal(StringHelper.displayEnergy(recipe.getEnergy()).get(0)));
        }
        return list;
    }

    @Override
    public boolean handleInput(GeneratorRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        return IRecipeCategory.super.handleInput(recipe, mouseX, mouseY, input);
    }

    @Override
    public boolean isHandled(GeneratorRecipe recipe) {
        return IRecipeCategory.super.isHandled(recipe);
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(GeneratorRecipe recipe) {
        return IRecipeCategory.super.getRegistryName(recipe);
    }
}
