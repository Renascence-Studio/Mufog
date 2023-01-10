package hlft.fabric.mufog.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static hlft.fabric.mufog.MufogMod.asId;

public class MFSounds {
    public static final Identifier FORGING_ID = asId("forging");
    public static final Identifier FORGING_FAIL_ID = asId("forging_fail");
    public static SoundEvent FORGING_SOUND = new SoundEvent(FORGING_ID);
    public static SoundEvent FORGING_FAIL_SOUND = new SoundEvent(FORGING_FAIL_ID);

    public static void init() {
        Registry.register(Registry.SOUND_EVENT, FORGING_ID, FORGING_SOUND);
        Registry.register(Registry.SOUND_EVENT, FORGING_FAIL_ID, FORGING_FAIL_SOUND);
    }
}
