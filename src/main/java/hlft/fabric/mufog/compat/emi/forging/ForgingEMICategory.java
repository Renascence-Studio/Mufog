package hlft.fabric.mufog.compat.emi.forging;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import hlft.fabric.mufog.MufogMod;
import hlft.fabric.mufog.compat.emi.MufogEMIPlugin;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.init.MFItems;

@SuppressWarnings("UnstableApiUsage")
public class ForgingEMICategory extends EmiRecipeCategory {

    public ForgingEMICategory() {
        super(MufogMod.asId(ForgingRecipe.FORGING_RECIPE_ID), EmiStack.of(MFItems.FORGING_ANVIL_ITEM), new EmiTexture(MufogEMIPlugin.TEXTURE, 0, 0, 16, 16), EmiRecipeSorting.compareOutputThenInput());
    }
}
