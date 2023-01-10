package hlft.fabric.mufog.misc;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonHelper;

import static net.minecraft.recipe.ShapedRecipe.outputFromJson;

public class MFItemStack {
    private final ItemStack itemstack;
    private final float chance;

    public MFItemStack(ItemStack stack, float chance) {
        this.itemstack = stack;
        this.chance = chance;
    }

    public ItemStack getItemstack() {
        return this.itemstack;
    }

    public float getChance() {
        return this.chance;
    }

    public static MFItemStack fromJson(JsonObject object) {
        return new MFItemStack(outputFromJson(object), JsonHelper.getFloat(object, "chance", 1.0F));
    }
}
