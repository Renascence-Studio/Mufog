package hlft.fabric.mufog.init;

import hlft.fabric.mufog.impl.block.ForgingAnvilBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import static hlft.fabric.mufog.MufogMod.asId;

public class MFBlocks {
    public static final Block FORGING_ANVIL_BLOCK = new ForgingAnvilBlock(FabricBlockSettings.copy(Blocks.ANVIL).requiresTool());

    public static void init() {
        Registry.register(Registry.BLOCK, asId("forging_anvil"), FORGING_ANVIL_BLOCK);
    }
}
