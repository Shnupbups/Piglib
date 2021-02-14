package com.shnupbups.piglib.rei.category;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;

import com.shnupbups.piglib.rei.PiglibREI;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PiglibDisplay implements RecipeDisplay {
	private List<EntryStack> entries;
	private Identifier category;

	public PiglibDisplay(List<ItemStack> entries, Identifier category) {
		this.entries = EntryStack.ofItemStacks(entries);
		this.category = category;
	}

	public List<EntryStack> getEntries() {
		return entries;
	}

	@Override
	public @NotNull List<List<EntryStack>> getInputEntries() {
		return Collections.singletonList(entries);
	}

	@Override
	public @NotNull List<List<EntryStack>> getResultingEntries() {
		return Collections.emptyList();
	}

	@Override
	public @NotNull Identifier getRecipeCategory() {
		return category;
	}
}
