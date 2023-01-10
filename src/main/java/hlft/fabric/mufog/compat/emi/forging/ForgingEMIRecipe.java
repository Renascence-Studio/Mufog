package hlft.fabric.mufog.compat.emi.forging;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.ItemEmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import hlft.fabric.mufog.compat.emi.MufogEMIPlugin;
import hlft.fabric.mufog.impl.item.HammerItem;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.misc.MFForgingHelper;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class ForgingEMIRecipe implements EmiRecipe {
    private static final EmiTexture BLUEPRINT = new EmiTexture(MufogEMIPlugin.TEXTURE, 17, 2, 14, 13);
    private static final EmiTexture NO_BLUEPRINT = new EmiTexture(MufogEMIPlugin.TEXTURE, 33, 2, 33, 13);
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final EmiIngredient blueprint;
    private final EmiIngredient hammer;
    private final EmiStack output;
    private final float chance;
    private final int hanmmerTimes;

    public ForgingEMIRecipe(ForgingRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)), EmiIngredient.of(recipe.getBlueprint()), EmiIngredient.of(HammerItem.tagFromLevel(recipe.getLevel())));
        this.blueprint = input.get(1);
        this.hammer = input.get(2);
        this.output = new ItemEmiStack(recipe.getOutput());
        this.chance = recipe.getChance();
        this.hanmmerTimes = recipe.getProcesstime();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MufogEMIPlugin.FORGING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 110;
    }

    @Override
    public int getDisplayHeight() {
        return 50;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(hammer, 13, 4);
        widgets.addSlot(output, 82, 26).appendTooltip(MFForgingHelper.translateChanceTip(chance));
        if (!blueprint.isEmpty()) {
            widgets.addTexture(BLUEPRINT, 39, 7).tooltip((x, y) -> List.of(TooltipComponent.of(EmiPort.ordered(new TranslatableText("tip.mufog.manage.need_blueprint").formatted(Formatting.BLUE)))));
            widgets.addSlot(blueprint, 56, 4).appendTooltip(new TranslatableText("tip.mufog.manage.no_need_blueprint").formatted(Formatting.BLUE));
        } else {
            widgets.addTexture(NO_BLUEPRINT, 39, 7).tooltip((x, y) -> List.of(TooltipComponent.of(EmiPort.ordered(new TranslatableText("tip.mufog.manage.no_need_blueprint").formatted(Formatting.GREEN)))));
        }
        widgets.addSlot(input.get(0), 13, 26);
        widgets.addFillingArrow(46, 27, 20 * 20).tooltip((x, y) -> List.of(TooltipComponent.of(EmiPort.ordered(new TranslatableText("tip.mufog.manage.hammertimes", hanmmerTimes).formatted(Formatting.YELLOW)))));
    }
}
