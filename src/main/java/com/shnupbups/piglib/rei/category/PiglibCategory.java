package com.shnupbups.piglib.rei.category;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import com.shnupbups.piglib.rei.PiglibREI;
import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.ScissorsHandler;
import me.shedaniel.clothconfig2.api.ScrollingContainer;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.REIHelper;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Slot;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.entries.RecipeEntry;
import me.shedaniel.rei.gui.widget.Widget;
import me.shedaniel.rei.gui.widget.WidgetWithBounds;
import me.shedaniel.rei.utils.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;

public class PiglibCategory implements RecipeCategory<PiglibDisplay> {
	private Identifier id;
	private EntryStack logo;
	private String name;

	public PiglibCategory(Identifier id, ItemConvertible logo, String name) {
		this.id = id;
		this.logo = EntryStack.create(logo);
		this.name = name;
	}

	@Override
	public @NotNull Identifier getIdentifier() {
		return id;
	}

	@Override
	public @NotNull EntryStack getLogo() {
		return logo;
	}

	@Override
	public @NotNull String getCategoryName() {
		return I18n.translate("category.rei.piglib."+name);
	}

	@Override
	public @NotNull RecipeEntry getSimpleRenderer(PiglibDisplay recipe) {
		String name = getCategoryName();
		return new RecipeEntry() {
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
	public @NotNull List<Widget> setupDisplay(PiglibDisplay display, Rectangle bounds) {
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
	public int getFixedRecipesPerPage() {
		return 1;
	}

	private static class ScrollableSlotsWidget extends WidgetWithBounds {
		private Rectangle bounds;
		private List<Slot> widgets;
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
		public boolean mouseScrolled(double double_1, double double_2, double double_3) {
			if (containsMouse(double_1, double_2)) {
				scrolling.offset(ClothConfigInitializer.getScrollStep() * -double_3, true);
				return true;
			}
			return false;
		}

		@NotNull
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
			scrolling.renderScrollBar(0xff000000, 1, REIHelper.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
			ScissorsHandler.INSTANCE.removeLastScissor();
		}

		@Override
		public List<? extends Element> children() {
			return widgets;
		}
	}
}
