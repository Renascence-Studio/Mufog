package hlft.fabric.mufog.impl.recipe;

import hlft.fabric.mufog.impl.block.ForgingAnvilTile;
import hlft.fabric.mufog.init.MFBlocks;
import hlft.fabric.mufog.misc.MFItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgingRecipe implements Recipe<ForgingAnvilTile> {
    public static final String FORGING_RECIPE_ID = "forging";
    protected final Ingredient ingredient;
    protected final Ingredient blueprint;
    protected final MFItemStack result;
    private final int processtime;
    private final int level;
    private final float chance;
    private final Identifier id;

    public ForgingRecipe(Identifier id, Ingredient ingredient, @Nullable Ingredient blueprint, MFItemStack result, int processtime, int level) {
        this.ingredient = ingredient;
        this.blueprint = (blueprint == null) ? Ingredient.EMPTY : blueprint;
        this.result = result;
        this.id = id;
        this.processtime = processtime;
        this.level = level;
        this.chance = result.getChance();
    }

    @Override
    public boolean matches(ForgingAnvilTile inventory, World world) {
        return this.ingredient.test(inventory.getStack(0)) && this.blueprint.test(inventory.getBlueprint());
    }

    @Override
    public ItemStack craft(ForgingAnvilTile inventory) {
        ItemStack itemStack = this.result.getItemstack();
        NbtCompound nbtCompound = inventory.getStack(0).getNbt();
        if (nbtCompound != null) {
            itemStack.setNbt(nbtCompound.copy());
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.result.getItemstack();
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(MFBlocks.FORGING_ANVIL_BLOCK);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ForgingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ForgingRecipeType.INSTANCE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public int getProcesstime() {
        return processtime;
    }

    public int getLevel() {
        return level;
    }

    public Ingredient getBlueprint() {
        return blueprint;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.ingredient);
        return defaultedList;
    }

    public float getChance() {
        return this.chance;
    }
}
