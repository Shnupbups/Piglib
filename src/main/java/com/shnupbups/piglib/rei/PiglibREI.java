package com.shnupbups.piglib.rei;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import com.shnupbups.piglib.Piglib;
import com.shnupbups.piglib.rei.category.PiglibCategory;
import com.shnupbups.piglib.rei.category.PiglibDisplay;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.utils.CollectionUtils;

import com.google.common.collect.Lists;
import java.util.List;

public class PiglibREI implements REIPluginV0 {
	public static final Identifier ID = Piglib.id("rei_plugin");

	public static final Identifier BARTERING = Piglib.id("bartering");
	public static final Identifier PIGLIN_SAFE_ARMOR = Piglib.id("piglin_safe_armor");
	public static final Identifier PIGLIN_LOVED = Piglib.id("piglin_loved");

	@Override
	public Identifier getPluginIdentifier() {
		return ID;
	}

	@Override
	public void registerPluginCategories(RecipeHelper recipeHelper) {
		recipeHelper.registerCategories(new PiglibCategory(BARTERING, Items.GOLD_INGOT, "bartering"));
		recipeHelper.registerCategories(new PiglibCategory(PIGLIN_SAFE_ARMOR, Items.GOLDEN_BOOTS, "piglin_safe_armor"));
		recipeHelper.registerCategories(new PiglibCategory(PIGLIN_LOVED, Items.GOLD_NUGGET, "piglin_loved"));
	}

	@Override
	public void registerRecipeDisplays(RecipeHelper recipeHelper) {
		recipeHelper.registerDisplay(new PiglibDisplay(CollectionUtils.map(Lists.newArrayList(Piglib.PIGLIN_BARTERING_ITEMS.values()), ItemStack::new), BARTERING));
		recipeHelper.registerDisplay(new PiglibDisplay(CollectionUtils.map(Lists.newArrayList(Piglib.PIGLIN_SAFE_ARMOR.values()), ItemStack::new), PIGLIN_SAFE_ARMOR));
		recipeHelper.registerDisplay(new PiglibDisplay(getPiglinLovedStacks(), PIGLIN_LOVED));
	}

	@Override
	public void registerOthers(RecipeHelper recipeHelper) {
		recipeHelper.removeAutoCraftButton(BARTERING);
		recipeHelper.removeAutoCraftButton(PIGLIN_SAFE_ARMOR);
		recipeHelper.removeAutoCraftButton(PIGLIN_LOVED);
	}

	public List<ItemStack> getPiglinLovedStacks() {
		List<ItemStack> list = CollectionUtils.map(Lists.newArrayList(ItemTags.PIGLIN_LOVED.values()), ItemStack::new);
		list.addAll(CollectionUtils.map(Lists.newArrayList(Piglib.PIGLIN_LOVED_NUGGETS.values()), (item) -> new ItemStack(item, 64)));
		return list;
	}
}
