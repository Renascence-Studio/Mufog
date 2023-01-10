package hlft.fabric.mufog.init;

import hlft.fabric.mufog.impl.item.HammerItem;
import hlft.fabric.mufog.misc.MFMaterials;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static hlft.fabric.mufog.MufogMod.asId;

public class MFItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final ItemGroup MAIN_TAB = FabricItemGroupBuilder
            .create(asId("main"))
            .icon(MFItems::getIconItem)
            .appendItems(itemStacks -> itemStacks.addAll(ITEMS.stream().map(ItemStack::new).toList()))
            .build();

    public static final BlockItem FORGING_ANVIL_ITEM = new BlockItem(MFBlocks.FORGING_ANVIL_BLOCK, new FabricItemSettings().group(MAIN_TAB));
    public static final Item IRON_HAMMER = new HammerItem(ToolMaterials.IRON);
    public static final Item GOLDEN_HAMMER = new HammerItem(ToolMaterials.GOLD);
    public static final Item DIAMOND_HAMMER = new HammerItem(ToolMaterials.DIAMOND);
    public static final Item NETHERITE_HAMMER = new HammerItem(ToolMaterials.NETHERITE);
    public static final Item COPPER_HAMMER = new HammerItem(MFMaterials.COPPER);

    public static void init() {
        createItem("forging_anvil", FORGING_ANVIL_ITEM);
        createItem("iron_hammer", IRON_HAMMER);
        createItem("golden_hammer", GOLDEN_HAMMER);
        createItem("diamond_hammer", DIAMOND_HAMMER);
        createItem("netherite_hammer", NETHERITE_HAMMER);
        createItem("copper_hammer", COPPER_HAMMER);
    }

    public static void createItem(String id, Item item) {
        ITEMS.add(Registry.register(Registry.ITEM, asId(id), item));
    }

    public static ItemStack getIconItem() {
        return NETHERITE_HAMMER.getDefaultStack();
    }
}
