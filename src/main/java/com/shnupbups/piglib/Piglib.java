package com.shnupbups.piglib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;

public class Piglib implements ModInitializer {
	public static final String MOD_ID = "piglib";

	public static Tag<Item> PIGLIN_SAFE_ARMOR;
	public static Tag<Item> PIGLIN_LOVED_NUGGETS;
	public static Tag<Item> PIGLIN_BARTERING_ITEMS;

	public static boolean shouldAdmire(ItemStack stack) {
		return stack.isIn(ItemTags.PIGLIN_LOVED);
	}

	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		PIGLIN_SAFE_ARMOR = TagFactory.ITEM.create(id("piglin_safe_armor"));
		PIGLIN_LOVED_NUGGETS = TagFactory.ITEM.create(id("piglin_loved_nuggets"));
		PIGLIN_BARTERING_ITEMS = TagFactory.ITEM.create(id("piglin_bartering_items"));
	}
}
