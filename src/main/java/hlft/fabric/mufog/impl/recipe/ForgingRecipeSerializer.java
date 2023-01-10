package hlft.fabric.mufog.impl.recipe;

import com.google.gson.JsonObject;
import hlft.fabric.mufog.misc.MFItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class ForgingRecipeSerializer implements RecipeSerializer<ForgingRecipe> {
    private ForgingRecipeSerializer() {
    }

    public static final ForgingRecipeSerializer INSTANCE = new ForgingRecipeSerializer();

    @Override
    public ForgingRecipe read(Identifier id, JsonObject jsonObject) {
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));
        Ingredient blueprint = Ingredient.EMPTY;
        if (jsonObject.has("blueprint"))
            blueprint = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "blueprint"));
        MFItemStack itemStack = MFItemStack.fromJson(JsonHelper.getObject(jsonObject, "result"));
        int processtime = jsonObject.get("processtime").getAsInt();
        int level = jsonObject.get("level").getAsInt();
        return new ForgingRecipe(id, ingredient, blueprint, itemStack, processtime, level);
    }

    @Override
    public ForgingRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        Ingredient blueprint = Ingredient.fromPacket(buf);
        MFItemStack itemStack = new MFItemStack(buf.readItemStack(), buf.readFloat());
        int processtime = buf.readInt();
        int level = buf.readInt();
        return new ForgingRecipe(id, ingredient, blueprint, itemStack, processtime, level);
    }

    @Override
    public void write(PacketByteBuf buf, ForgingRecipe recipe) {
        recipe.ingredient.write(buf);
        recipe.blueprint.write(buf);
        buf.writeItemStack(recipe.result.getItemstack());
        buf.writeFloat(recipe.getChance());
        buf.writeInt(recipe.getProcesstime());
        buf.writeInt(recipe.getLevel());
    }
}
