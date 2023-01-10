package hlft.fabric.mufog.compat.kubejs.js;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.ListJS;

public class ForgingKJS extends RecipeJS {
    @Override
    public void create(ListJS args) {
        this.outputItems.add(this.parseResultItem(args.get(0)));
        this.inputItems.add(this.parseIngredientItem(args.get(1)));

        Number hammerTimes = args.size() > 2 ? (Number) args.get(2) : 5;
        Number hammerLevel = args.size() > 3 ? (Number) args.get(3) : 1;

        if (args.size() > 4)
            this.inputItems.add(this.parseIngredientItem(args.get(4)));

        json.addProperty("processtime", hammerTimes);
        json.addProperty("level", hammerLevel);
    }

    @Override
    public void deserialize() {
        this.outputItems.add(this.parseResultItem(this.json.get("result")));
        this.inputItems.add(this.parseIngredientItem(this.json.get("ingredient")));
        this.inputItems.add(this.parseIngredientItem(this.json.get("blueprint")));
    }

    @Override
    public void serialize() {
        if (this.serializeOutputs) {
            this.json.add("result", this.outputItems.get(0).toResultJson());
        }

        if (this.serializeInputs) {
            this.json.add("ingredient", this.inputItems.get(0).toJson());
            this.json.add("blueprint", this.inputItems.get(1).toJson());
        }
    }
}
