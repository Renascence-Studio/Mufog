package hlft.fabric.mufog.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.text.NumberFormat;
import java.util.List;

public class MFForgingHelper {
    public static void blueprintTip(NbtCompound tag, List<Text> tooltip) {
        ItemStack blueprint = ItemStack.fromNbt(tag);
        String key = !blueprint.isEmpty() ? blueprint.getTranslationKey() : "tip.mufog.forging.blueprint.air";
        var text = new TranslatableText("tip.mufog.forging.blueprint");
        text.append(new TranslatableText(key)).formatted(Formatting.BLUE);
        tooltip.add(text);
    }

    public static void resultTip(NbtCompound tag, List<Text> tooltip) {
        ItemStack result = ItemStack.fromNbt(tag);
        String key = !result.isEmpty() ? result.getTranslationKey() : "tip.mufog.forging.blueprint.air";
        var text = new TranslatableText("tip.mufog.forging.output");
        text.append(new TranslatableText(key)).formatted(Formatting.GREEN);
        tooltip.add(text);
    }

    public static void progressTip(float progress, List<Text> tooltip) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(1);
        var text = new TranslatableText("tip.mufog.forging.progress");
        text.append(fmt.format(progress)).formatted(Formatting.YELLOW);
        tooltip.add(text);
    }

    public static NbtCompound defaultNBT() {
        NbtCompound nbt = new NbtCompound();

        nbt.putInt("Times", 0);
        nbt.put("Blueprint", ItemStack.EMPTY.writeNbt(new NbtCompound()));
        nbt.put("Result", ItemStack.EMPTY.writeNbt(new NbtCompound()));
        nbt.putFloat("Progress", 0F);

        return nbt;
    }

    public static Text translateChanceTip(float chance) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        return new TranslatableText("tip.mufog.manage.chance").append(fmt.format(chance)).append(new TranslatableText("tip.mufog.manage.full_stop")).formatted(Formatting.YELLOW);
    }
}
