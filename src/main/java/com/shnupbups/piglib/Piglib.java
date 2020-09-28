package com.shnupbups.piglib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;

import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class Piglib implements ModInitializer {
	public static final String MOD_ID = "piglib";

	public static Tag<Item> PIGLIN_SAFE_ARMOR;
	public static Tag<Item> PIGLIN_LOVED_NUGGETS;
	public static Tag<Item> PIGLIN_BARTERING_ITEMS;

	public static boolean shouldAdmire(Item item) {
		return item.isIn(ItemTags.PIGLIN_LOVED) || item.isIn(Piglib.PIGLIN_BARTERING_ITEMS);
	}

	@Override
	public void onInitialize() {
		PIGLIN_SAFE_ARMOR = TagRegistry.item(id("piglin_safe_armor"));
		PIGLIN_LOVED_NUGGETS = TagRegistry.item(id("piglin_loved_nuggets"));
		PIGLIN_BARTERING_ITEMS = TagRegistry.item(id("piglin_bartering_items"));
	}
	
	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}
}
