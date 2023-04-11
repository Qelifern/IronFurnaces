package ironfurnaces.recipes;

import com.google.gson.JsonObject;
import ironfurnaces.init.Registration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GeneratorRecipe implements Recipe<Container> {

    private final ResourceLocation recipeId;
    private int energy;
    private ItemStack stack;

    public GeneratorRecipe(ResourceLocation recipeId, int energy, ItemStack stack)
    {
        this.recipeId = recipeId;
        this.energy = energy;
        this.stack = stack;
    }

    @Override
    public boolean isIncomplete() {
        return stack.isEmpty();
    }

    public ItemStack getIngredient()
    {
        return stack;
    }

    public int getEnergy()
    {
        return energy;
    }

    public static int getTotalCount(Container inventory, ItemStack input) {
        ItemStack stack = inventory.getItem(0);
        if (!stack.isEmpty() && stack.getItem() == input.getItem()) {
            return stack.getCount();
        }
        return 0;
    }


    @Override
    public boolean matches(Container inv, Level level) {
        int required = stack.getCount();
        int found = getTotalCount(inv, stack);
        return found >= required;
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return stack;
    }
    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.GENERATOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.GENERATOR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<GeneratorRecipe> {
        @Override
        public GeneratorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int energy = GsonHelper.getAsInt(json, "energy", 10000);
            ItemStack input = ShapedRecipe.itemStackFromJson(json);
            GeneratorRecipe recipe = new GeneratorRecipe(recipeId, energy, input);
            return recipe;
        }

        @Nullable
        @Override
        public GeneratorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            GeneratorRecipe recipe = new GeneratorRecipe(recipeId, buffer.readVarInt(), buffer.readItem());
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, GeneratorRecipe recipe) {
            buffer.writeVarInt(recipe.energy);
            buffer.writeItem(recipe.stack);
        }
    }
}
