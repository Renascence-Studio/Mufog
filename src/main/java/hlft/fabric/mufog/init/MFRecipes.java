package hlft.fabric.mufog.init;

import hlft.fabric.mufog.MufogMod;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.impl.recipe.ForgingRecipeSerializer;
import hlft.fabric.mufog.impl.recipe.ForgingRecipeType;
import net.minecraft.util.registry.Registry;

public class MFRecipes {

    public static void init() {
        Registry.register(Registry.RECIPE_SERIALIZER, MufogMod.asId(ForgingRecipe.FORGING_RECIPE_ID), ForgingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, MufogMod.asId(ForgingRecipe.FORGING_RECIPE_ID), ForgingRecipeType.INSTANCE);
    }
}
