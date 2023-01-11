package hlft.fabric.mufog.client;

import hlft.fabric.mufog.client.renderer.ForgingAnvilRenderer;
import hlft.fabric.mufog.init.MFBlockEntities;
import hlft.fabric.mufog.misc.MFForgingHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.nbt.NbtCompound;

@Environment(EnvType.CLIENT)
public class MufogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MFBlockEntities.FORGING_ANVIL_TILE, ForgingAnvilRenderer::new);
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            NbtCompound tag = stack.getSubNbt("Forging");
            if (tag != null) {
                MFForgingHelper.blueprintTip(tag.getCompound("Blueprint"), lines);
                MFForgingHelper.resultTip(tag.getCompound("Result"), lines);
                MFForgingHelper.progressTip(tag.getFloat("Progress"), lines);
            }
        });
    }
}
