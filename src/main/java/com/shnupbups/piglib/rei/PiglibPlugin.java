package com.shnupbups.piglib.rei;

import com.shnupbups.piglib.Piglib;
import com.shnupbups.piglib.rei.display.*;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;

public class PiglibPlugin implements REIServerPlugin {
	public static final CategoryIdentifier<BarteringDisplay> BARTERING = CategoryIdentifier.of(Piglib.MOD_ID, "plugins/bartering");
	public static final CategoryIdentifier<PiglinLovedDisplay> PIGLIN_LOVED = CategoryIdentifier.of(Piglib.MOD_ID, "plugins/piglin_loved");
	public static final CategoryIdentifier<PiglinSafeArmorDisplay> PIGLIN_SAFE_ARMOR = CategoryIdentifier.of(Piglib.MOD_ID, "plugins/piglin_safe_armor");
	public static final CategoryIdentifier<PiglinRepellentsDisplay> PIGLIN_REPELLENTS = CategoryIdentifier.of(Piglib.MOD_ID, "plugins/piglin_repellents");

	@Override
	public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
		registry.register(BARTERING, PiglibDisplay.serializer(BarteringDisplay::new));
		registry.register(PIGLIN_LOVED, PiglibDisplay.serializer(PiglinLovedDisplay::new));
		registry.register(PIGLIN_SAFE_ARMOR, PiglibDisplay.serializer(PiglinSafeArmorDisplay::new));
		registry.register(PIGLIN_REPELLENTS, PiglibDisplay.serializer(PiglinRepellentsDisplay::new));
	}
}
