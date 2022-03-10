package com.shnupbups.piglib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;

public class Piglib implements ModInitializer {
	public static final String MOD_ID = "piglib";

	public static TagKey<Item> PIGLIN_SAFE_ARMOR = TagKey.of(Registry.ITEM_KEY, id("piglin_safe_armor"));
	public static TagKey<Item> PIGLIN_LOVED_NUGGETS = TagKey.of(Registry.ITEM_KEY, id("piglin_loved_nuggets"));
	public static TagKey<Item> PIGLIN_BARTERING_ITEMS = TagKey.of(Registry.ITEM_KEY, id("piglin_bartering_items"));

	public static boolean shouldAdmire(ItemStack stack) {
		return stack.isIn(ItemTags.PIGLIN_LOVED);
	}

	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		// NO-OP
	}
}
