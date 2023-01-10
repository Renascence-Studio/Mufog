package hlft.fabric.mufog.impl.block;

import hlft.fabric.mufog.impl.item.HammerItem;
import hlft.fabric.mufog.impl.recipe.ForgingRecipe;
import hlft.fabric.mufog.init.MFSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ForgingAnvilBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public ForgingAnvilBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        ItemStack handStack = player.getStackInHand(hand);
        ItemStack storedStack;
        if (!(world.getBlockEntity(pos) instanceof ForgingAnvilTile tile))
            return ActionResult.PASS;

        if (result.getSide() == Direction.UP) {
            storedStack = tile.getItem();
            if (handStack.isEmpty() && storedStack.isEmpty()) {
                return ActionResult.PASS;
            } else if (handStack.isEmpty()) {
                if (!player.getInventory().insertStack(tile.removeItem())) {
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tile.removeItem());
                }
                return ActionResult.success(world.isClient());
            } else if (storedStack.isEmpty()) {
                tile.addItem(handStack);
                return ActionResult.success(world.isClient());
            } else {
                if (handStack.getItem() == storedStack.getItem()) {
                    return ActionResult.CONSUME;
                } else if (HammerItem.isForgingHammer(handStack)) {
                    if (player.getItemCooldownManager().isCoolingDown(handStack.getItem()))
                        return ActionResult.CONSUME;
                    return craftRecipe(player.getStackInHand(Hand.OFF_HAND), handStack, player, tile);
                } else {
                    if (!player.getInventory().insertStack(tile.removeItem())) {
                        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tile.removeItem());
                    }
                    tile.addItem(handStack);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH, SOUTH -> VoxelShapes.cuboid(2.5 / 16, 0, 0, 13.5 / 16, 11.0 / 16, 1);
            case EAST, WEST -> VoxelShapes.cuboid(0, 0, 2.5 / 16, 1, 11.0 / 16, 13.5 / 16);
            default -> VoxelShapes.fullCube();
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgingAnvilTile(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ForgingAnvilTile) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public ActionResult craftRecipe(ItemStack offItem, ItemStack hammer, PlayerEntity player, ForgingAnvilTile tile) {

        World world = tile.getWorld();

        if (world == null) {
            return ActionResult.FAIL;
        }

        ItemStack blueprint = new ItemStack(offItem.getItem(), 1);

        tile.loadBlueprint(blueprint);

        Optional<ForgingRecipe> optional = world.getRecipeManager().getFirstMatch(tile.recipeType, tile, world);

        if (optional.isPresent()) {
            NbtCompound nbt = tile.getItem().getOrCreateNbt();

            ForgingRecipe recipe = optional.get();

            NbtCompound forgingNbt = nbt.getCompound("Forging");

            int nowProcessTimes = forgingNbt.getInt("Times");
            int exProcessTimes = recipe.getProcesstime();

            NbtCompound blueprintNbt = forgingNbt.getCompound("Blueprint");

            ItemStack nowBlueprint = forgingNbt.contains("Blueprint") ? ItemStack.fromNbt(blueprintNbt) : ItemStack.EMPTY;

            if (nowBlueprint.isEmpty() && nowProcessTimes == 0) {
                forgingNbt.put("Blueprint", blueprint.writeNbt(new NbtCompound()));
                nowBlueprint = blueprint;
            }

            if (nowBlueprint.getItem() != blueprint.getItem()) {
                return ActionResult.CONSUME;
            }

            boolean flag = recipe.getBlueprint().test(blueprint);
            boolean flag_blueprint = (nowBlueprint.getItem() == blueprint.getItem());

            if (!flag || !flag_blueprint) {
                return ActionResult.CONSUME;
            }

            //锻造完成部分
            if (exProcessTimes - 1 == nowProcessTimes) {
                SoundEvent event = MFSounds.FORGING_SOUND;
                float volume = 0.6F;

                if (world.random.nextFloat() <= recipe.getChance()) {
                    tile.setStack(0, recipe.getOutput());
                } else {
                    tile.setStack(0, ItemStack.EMPTY);
                    event = MFSounds.FORGING_FAIL_SOUND;
                    volume = 1.3F;
                }

                if (!world.isClient()) {
                    world.playSound(null, tile.getPos(), event, SoundCategory.PLAYERS, volume, 1.0f);
                }
            }

            int hammerLevel = recipe.getLevel();

            if (HammerItem.getForgingLevel(hammer) < hammerLevel) {
                return ActionResult.CONSUME;
            }

            //锻造进行部分
            if (HammerItem.getForgingLevel(hammer) >= hammerLevel) {
                forgingNbt.putInt("Times", nowProcessTimes + 1);
                forgingNbt.put("Blueprint", blueprint.writeNbt(new NbtCompound()));
                forgingNbt.put("Result", recipe.getOutput().writeNbt(new NbtCompound()));
                forgingNbt.putFloat("Progress", (((float) nowProcessTimes + 1) / (float) exProcessTimes));

                if (!world.isClient()) {
                    world.playSound(null, tile.getPos(), MFSounds.FORGING_SOUND, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }

            if (player != null) {
                hammer.damage(1, player, user -> user.sendToolBreakStatus(Hand.MAIN_HAND));
                if (!player.getAbilities().creativeMode) {
                    player.getItemCooldownManager().set(hammer.getItem(), 30);
                }
            } else {
                if (hammer.damage(1, world.getRandom(), null)) {
                    hammer.setCount(0);
                }
            }

            nbt.put("Forging", forgingNbt);
            return ActionResult.success(world.isClient());
        }
        return ActionResult.PASS;
    }
}
