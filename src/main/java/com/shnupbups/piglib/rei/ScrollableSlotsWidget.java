package com.shnupbups.piglib.rei;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.ScissorsHandler;
import me.shedaniel.clothconfig2.api.ScrollingContainer;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;

public class ScrollableSlotsWidget extends WidgetWithBounds {
	private final Rectangle bounds;
	private final List<Slot> widgets;
	private final ScrollingContainer scrolling = new ScrollingContainer() {
		@Override
		public Rectangle getBounds() {
			Rectangle bounds = ScrollableSlotsWidget.this.getBounds();
			return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
		}

		@Override
		public int getMaxScrollHeight() {
			return MathHelper.ceil(widgets.size() / 8f) * 18;
		}
	};

	public ScrollableSlotsWidget(Rectangle bounds, List<Slot> widgets) {
		this.bounds = Objects.requireNonNull(bounds);
		this.widgets = Lists.newArrayList(widgets);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if (containsMouse(mouseX, mouseY)) {
			scrolling.offset(ClothConfigInitializer.getScrollStep() * -amount, true);
			return true;
		}
		return false;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (scrolling.updateDraggingState(mouseX, mouseY, button))
			return true;
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
			return true;
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		scrolling.updatePosition(delta);
		Rectangle innerBounds = scrolling.getScissorBounds();
		ScissorsHandler.INSTANCE.scissor(innerBounds);
		for (int y = 0; y < MathHelper.ceil(widgets.size() / 8f); y++) {
			for (int x = 0; x < 8; x++) {
				int index = y * 8 + x;
				if (widgets.size() <= index)
					break;
				Slot widget = widgets.get(index);
				widget.getBounds().setLocation(bounds.x + 1 + x * 18, (int) (bounds.y + 1 + y * 18 - scrolling.scrollAmount));
				widget.render(matrices, mouseX, mouseY, delta);
			}
		}
		ScissorsHandler.INSTANCE.removeLastScissor();
		ScissorsHandler.INSTANCE.scissor(scrolling.getBounds());
		scrolling.renderScrollBar(0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
		ScissorsHandler.INSTANCE.removeLastScissor();
	}

	@Override
	public List<? extends Element> children() {
		return widgets;
	}
}