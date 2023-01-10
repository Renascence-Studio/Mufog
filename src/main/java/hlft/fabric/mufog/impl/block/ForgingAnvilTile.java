package hlft.fabric.mufog.impl.block;

import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.impl.recipe.ForgingRecipeType;
import hlft.fabric.mufog.init.MFBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class ForgingAnvilTile extends BlockEntity implements ForgingAnvilInv {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> blueprint = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final RecipeType<ForgingRecipe> recipeType = ForgingRecipeType.INSTANCE;


    public ForgingAnvilTile(BlockPos pos, BlockState state) {
        super(MFBlockEntities.FORGING_ANVIL_TILE, pos, state);
    }

    @NotNull
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        NbtCompound nbtCompound = new NbtCompound();
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            items.set(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }

        return false;
    }

    public void clean() {
        items.set(0, ItemStack.EMPTY);
        inventoryChanged();
    }

    public ItemStack getItem() {
        return items.get(0);
    }

    public ItemStack getBlueprint() {
        return blueprint.get(0);
    }

    public void loadBlueprint(ItemStack stack) {
        blueprint.set(0, stack);
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getItem().split(1);
            inventoryChanged();

            return item;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void inventoryChanged() {
        markDirty();
        if (world != null) {
            world.updateListeners(getPos(), getCachedState(), getCachedState(), (1) | (1 << 1));
        }
    }
}
