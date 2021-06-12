package com.shnupbups.piglib.rei.category;

import com.google.common.collect.Lists;
import com.shnupbups.piglib.rei.ScrollableSlotsWidget;
import com.shnupbups.piglib.rei.display.BarteringDisplay;
import com.shnupbups.piglib.rei.display.PiglibDisplay;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

public abstract class PiglibCategory<T extends PiglibDisplay> implements DisplayCategory<T> {
	@Override
	public DisplayRenderer getDisplayRenderer(T display) {
		Text name = getTitle();
		return new DisplayRenderer() {
			@Override
			public int getHeight() {
				return 10 + MinecraftClient.getInstance().textRenderer.fontHeight;
			}

			@Override
			public void render(MatrixStack matrices, Rectangle rectangle, int mouseX, int mouseY, float delta) {
				MinecraftClient.getInstance().textRenderer.draw(matrices, name, rectangle.x + 5, rectangle.y + 6, -1);
			}
		};
	}

	@Override
	public List<Widget> setupDisplay(T display, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		Rectangle rectangle = new Rectangle(bounds.getCenterX() - (bounds.width / 2) - 1, bounds.y + 3, bounds.width + 2, bounds.height - 8);
		widgets.add(Widgets.createSlotBase(rectangle));
		widgets.add(new ScrollableSlotsWidget(rectangle, CollectionUtils.map(display.getEntries(), t -> Widgets.createSlot(new Point(0, 0)).disableBackground().entry(t))));
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 140;
	}

	@Override
	public int getFixedDisplaysPerPage() {
		return 1;
	}
}