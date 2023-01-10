package hlft.fabric.mufog.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import hlft.fabric.mufog.MufogMod;
import hlft.fabric.mufog.compat.emi.forging.ForgingEMICategory;
import hlft.fabric.mufog.compat.emi.forging.ForgingEMIRecipe;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.impl.recipe.ForgingRecipeType;
import hlft.fabric.mufog.init.MFBlocks;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class MufogEMIPlugin implements EmiPlugin {
    public static final Identifier TEXTURE = MufogMod.asId("textures/gui/manage/forging_emi_widgets.png");

    public static final EmiRecipeCategory FORGING = new ForgingEMICategory();

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FORGING);
        registry.addWorkstation(FORGING, EmiStack.of(MFBlocks.FORGING_ANVIL_BLOCK));

        RecipeManager manager = registry.getRecipeManager();

        for (ForgingRecipe recipe : manager.listAllOfType(ForgingRecipeType.INSTANCE)) {
            registry.addRecipe(new ForgingEMIRecipe(recipe));
        }
    }
}
