package hlft.fabric.mufog.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;
import hlft.fabric.mufog.compat.kubejs.js.ForgingKJS;

import static hlft.fabric.mufog.MufogMod.asId;
import static hlft.fabric.mufog.impl.recipe.ForgingRecipe.FORGING_RECIPE_ID;

public class MufogKJSPlugin extends KubeJSPlugin {
    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {
        event.register(asId(FORGING_RECIPE_ID), ForgingKJS::new);
    }
}
