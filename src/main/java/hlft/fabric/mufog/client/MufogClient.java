package hlft.fabric.mufog.client;

import hlft.fabric.mufog.client.renderer.ForgingAnvilRenderer;
import hlft.fabric.mufog.init.MFBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class MufogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MFBlockEntities.FORGING_ANVIL_TILE, ForgingAnvilRenderer::new);
    }
}
