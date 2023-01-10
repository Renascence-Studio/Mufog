package hlft.fabric.mufog.mixin;

import hlft.fabric.mufog.misc.MFForgingHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class MufogItemMixin {
    @Inject(at = @At("HEAD"), method = "appendTooltip")
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        NbtCompound tag = stack.getSubNbt("Forging");
        if (tag != null) {
            MFForgingHelper.blueprintTip(tag.getCompound("Blueprint"), tooltip);
            MFForgingHelper.resultTip(tag.getCompound("Result"), tooltip);
            MFForgingHelper.progressTip(tag.getFloat("Progress"), tooltip);
        }
    }
}
