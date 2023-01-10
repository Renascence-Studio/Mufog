package hlft.fabric.mufog.impl.recipe;

import net.minecraft.recipe.RecipeType;

public class ForgingRecipeType implements RecipeType<ForgingRecipe> {
    private ForgingRecipeType() {
    }

    public static final ForgingRecipeType INSTANCE = new ForgingRecipeType();
}
