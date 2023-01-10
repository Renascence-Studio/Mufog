package hlft.fabric.mufog.compat.rei.forging;

import com.google.common.collect.ImmutableList;
import hlft.fabric.mufog.compat.rei.MufogREIPlugin;
import hlft.fabric.mufog.impl.item.HammerItem;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static hlft.fabric.mufog.misc.MFForgingHelper.translateChanceTip;

@Environment(EnvType.CLIENT)
public class ForgingREIDisplay extends BasicDisplay {

    private final EntryIngredient blueprint;
    private final EntryIngredient hammer;
    private final int hammerTimes;

    public ForgingREIDisplay(ForgingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput()).map(stack -> stack.copy().tooltip(translateChanceTip(recipe.getChance())))));

        blueprint = EntryIngredients.ofIngredient(recipe.getBlueprint()).map(stack -> stack.copy().tooltip(new TranslatableText("tip.mufog.manage.blueprint").formatted(Formatting.BLUE)));

        hammer = EntryIngredients.ofItemTag(HammerItem.tagFromLevel(recipe.getLevel()));
        hammerTimes = recipe.getProcesstime();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MufogREIPlugin.FORGING;
    }

    @Override
    public List<EntryIngredient> getRequiredEntries() {
        List<EntryIngredient> requiredEntries = new ArrayList<>(super.getRequiredEntries());
        requiredEntries.add(getBlueprint());
        requiredEntries.add(getHammer());

        return ImmutableList.copyOf(requiredEntries);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        inputEntryList.add(getBlueprint());
        inputEntryList.add(getHammer());

        return ImmutableList.copyOf(inputEntryList);
    }

    public EntryIngredient getBlueprint() {
        return blueprint;
    }

    public List<EntryIngredient> getIngredientEntries() {
        return super.getInputEntries();
    }

    public EntryIngredient getHammer() {
        return hammer;
    }

    public int getHammerTimes() {
        return hammerTimes;
    }

}
