package hlft.fabric.mufog.init;

import hlft.fabric.mufog.impl.block.ForgingAnvilTile;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static hlft.fabric.mufog.MufogMod.asId;

public class MFBlockEntities {
    public static final BlockEntityType<ForgingAnvilTile> FORGING_ANVIL_TILE =
            FabricBlockEntityTypeBuilder.create(ForgingAnvilTile::new, MFBlocks.FORGING_ANVIL_BLOCK).build();

    public static void init() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, asId("forging_anvil"), FORGING_ANVIL_TILE);
    }
}
