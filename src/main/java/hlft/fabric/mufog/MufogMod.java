package hlft.fabric.mufog;

import hlft.fabric.mufog.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class MufogMod implements ModInitializer {
    public static final String MOD_ID = "mufog";

    @Override
    public void onInitialize() {
        MFBlockEntities.init();
        MFRecipes.init();
        MFBlocks.init();
        MFItems.init();
        MFSounds.init();
    }

    public static Identifier asId(String path) {
        return new Identifier(MOD_ID, path);
    }
}
