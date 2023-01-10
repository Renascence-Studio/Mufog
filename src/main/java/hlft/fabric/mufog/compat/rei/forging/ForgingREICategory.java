package hlft.fabric.mufog.compat.rei.forging;

import hlft.fabric.mufog.MufogMod;
import hlft.fabric.mufog.compat.rei.MufogREIPlugin;
import hlft.fabric.mufog.compat.rei.widgets.ForgedTipREIWidget;
import hlft.fabric.mufog.init.MFItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public class ForgingREICategory implements DisplayCategory<ForgingREIDisplay> {
    private static final Identifier GUI_TEXTURE = MufogMod.asId("textures/gui/manage/forging_anvil.png");
    private static final Identifier GUI_TEXTURE_BLUEPRINT = MufogMod.asId("textures/gui/manage/forging_anvil_blueprint.png");


    @Override
    public CategoryIdentifier<? extends ForgingREIDisplay> getCategoryIdentifier() {
        return MufogREIPlugin.FORGING;
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("block.mufog.forging_anvil");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MFItems.FORGING_ANVIL_ITEM);
    }

    @Override
    public List<Widget> setupDisplay(ForgingREIDisplay display, Rectangle bounds) {
        Point origin = bounds.getLocation();
        final List<Widget> widgets = new ArrayList<>();

        boolean flag = !display.getBlueprint().isEmpty();

        widgets.add(Widgets.createRecipeBase(bounds));

        /*
        添加一个基础的坐标点，
        （width：98，height：57）为材质上所有部件都包含在内的矩形的面积，
        注1：这里的98正确为91，98会让整体向左偏移7像素（我刻意而为之）。
        详细可查看assets/mufog/textures/gui/rei/forging_anvil_explain.png。
        注2：Explain的所有颜色线以左上角为基准。
         */
        Rectangle bgBounds = centeredIntoRecipeBase(new Point(origin.x, origin.y), 98, 57);

        //简单地判断有无蓝图，从而渲染不同的背景
        if (flag) {
            /*
            添加背景（有蓝图），
            这里的W&H同bgBounds，
            （u：9，v：4）为开始渲染的坐标点（即渲染起点）。
            注：坐标点以材质左上角为原点。
             */
            widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE_BLUEPRINT, new Rectangle(bgBounds.x, bgBounds.y, 91, 57), 9, 4));

            //添加蓝图槽位
            widgets.add(Widgets.createSlot(new Point(bgBounds.x + 57 - 9, bgBounds.y + 1))
                    .entries(display.getBlueprint()).markInput().disableBackground());

            //伪造组件（无渲染），然后添加绑定伪造组件位置的Tip
            ForgedTipREIWidget blueprint = new ForgedTipREIWidget(new Rectangle(bgBounds.x + 38 - 9, bgBounds.y + 5 - 4, 16, 16));
            widgets.add(Widgets.withTooltip(blueprint, new TranslatableText("tip.mufog.manage.need_blueprint").formatted(Formatting.BLUE)));

        } else {
            //添加背景（无蓝图）
            widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, new Rectangle(bgBounds.x, bgBounds.y, 91, 57), 9, 4));

            //伪造组件（无渲染），然后添加绑定伪造组件的Tip
            ForgedTipREIWidget checkMark = new ForgedTipREIWidget(new Rectangle(bgBounds.x + 39 - 9, bgBounds.y + 7 - 4, 33, 13));
            widgets.add(Widgets.withTooltip(checkMark, new TranslatableText("tip.mufog.manage.no_need_blueprint").formatted(Formatting.GREEN)));
        }

        //添加箭头组件
        Arrow arrow = Widgets.createArrow(new Point(bgBounds.x + 45 - 9, bgBounds.y + 27 - 4)).animationDurationTicks(20);
        widgets.add(arrow);

        //添加绑定箭头组件位置的Tip
        widgets.add(Widgets.withTooltip(arrow, new TranslatableText("tip.mufog.manage.hammertimes", display.getHammerTimes()).formatted(Formatting.YELLOW)));

        //添加锻造锤槽位
        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 5, bgBounds.y + 1))
                .entries(display.getHammer()).markInput().disableBackground());

        //添加输入槽位
        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 5, bgBounds.y + 23))
                .entries(display.getIngredientEntries().get(0)).markInput().disableBackground());

        //添加输出槽位（不适配多输出）
        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 85 - 11, bgBounds.y + 23))
                .entries(display.getOutputEntries().get(0)).markOutput().disableBackground());

        return widgets;
    }

    public static Rectangle centeredIntoRecipeBase(Point origin, int width, int height) {
        return centeredInto(new Rectangle(origin.x, origin.y, 150, 74), width, height);
    }

    public static Rectangle centeredInto(Rectangle origin, int width, int height) {
        return new Rectangle(origin.x + (origin.width - width) / 2, origin.y + (origin.height - height) / 2, width, height);
    }

    /*
    略大约材质高度
     */
    @Override
    public int getDisplayHeight() {
        return 73;
    }
}
