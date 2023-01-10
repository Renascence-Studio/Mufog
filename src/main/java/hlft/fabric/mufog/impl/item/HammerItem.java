package hlft.fabric.mufog.impl.item;

import hlft.fabric.mufog.init.MFItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import static hlft.fabric.mufog.MufogMod.asId;

public class HammerItem extends MiningToolItem {
    public static final TagKey<Item> HAMMER = TagKey.of(Registry.ITEM_KEY, asId("hammer"));
    public static final TagKey<Item> LEVEL_1 = TagKey.of(Registry.ITEM_KEY, asId("hammer/level_1"));
    public static final TagKey<Item> LEVEL_2 = TagKey.of(Registry.ITEM_KEY, asId("hammer/level_2"));
    public static final TagKey<Item> LEVEL_3 = TagKey.of(Registry.ITEM_KEY, asId("hammer/level_3"));
    public static final TagKey<Item> LEVEL_4 = TagKey.of(Registry.ITEM_KEY, asId("hammer/level_4"));

    public HammerItem(ToolMaterial material) {
        super(5.5F, -3.2F, material, BlockTags.PICKAXE_MINEABLE, new FabricItemSettings().group(MFItems.MAIN_TAB));
    }

    public static boolean isForgingHammer(ItemStack item) {
        return item.isIn(HAMMER) && item.isDamageable();
    }

    public static int getForgingLevel(ItemStack stack) {
        int level = 0;
        if (isForgingHammer(stack)) {
            if (stack.isIn(LEVEL_1)) {
                level = 1;
            }
            if (stack.isIn(LEVEL_2)) {
                level = 2;
            }
            if (stack.isIn(LEVEL_3)) {
                level = 3;
            }
            if (stack.isIn(LEVEL_4)) {
                level = 4;
            }
        }
        return level;
    }

    public static TagKey<Item> tagFromLevel(int i) {
        if (i <= 1) {
            return LEVEL_1;
        } else if (i == 2) {
            return LEVEL_2;
        } else if (i == 3) {
            return LEVEL_3;
        } else {
            return LEVEL_4;
        }
    }

}
