package hlft.fabric.mufog.compat.rei;

import hlft.fabric.mufog.MufogMod;
import hlft.fabric.mufog.compat.rei.forging.ForgingREICategory;
import hlft.fabric.mufog.compat.rei.forging.ForgingREIDisplay;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.impl.recipe.ForgingRecipeType;
import hlft.fabric.mufog.init.MFItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MufogREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<ForgingREIDisplay> FORGING = CategoryIdentifier.of(MufogMod.MOD_ID, "plugins/cutting");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ForgingREICategory());
        registry.addWorkstations(FORGING, EntryStacks.of(MFItems.FORGING_ANVIL_ITEM));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ForgingRecipe.class, ForgingRecipeType.INSTANCE, ForgingREIDisplay::new);
    }
}
