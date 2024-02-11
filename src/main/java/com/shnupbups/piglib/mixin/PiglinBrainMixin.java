/*
 * MIT License
 *
 * Copyright (c) 2020 Chainmail Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shnupbups.piglib.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.shnupbups.piglib.Piglib;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {
	@ModifyReturnValue(method = "acceptsForBarter(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
	private static boolean acceptsForBarterModify(boolean original, ItemStack stack) {
		if (stack.isIn(Piglib.PIGLIN_BARTERING_ITEMS)) return true;
		return original;
	}

	@Inject(method = "wearsGoldArmor(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true)
	private static void wearsGoldArmorInject(LivingEntity entity, CallbackInfoReturnable<Boolean> cir, @Local ItemStack stack) {
		if (stack.isIn(Piglib.PIGLIN_SAFE_ARMOR)) cir.setReturnValue(true);
	}

	@Redirect(method = "loot(Lnet/minecraft/entity/mob/PiglinEntity;Lnet/minecraft/entity/ItemEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean nuggetLootRedirect(ItemStack stack, Item item) {
		return stack.isOf(item) || stack.isIn(Piglib.PIGLIN_LOVED_NUGGETS);
	}

	@Inject(method = "canGather(Lnet/minecraft/entity/mob/PiglinEntity;Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), cancellable = true)
	private static void canGatherInject(PiglinEntity piglin, ItemStack stack, CallbackInfoReturnable<Boolean> cir, @Local boolean bl) {
		// bl = piglin.canInsertIntoInventory(stack)
		if (stack.isIn(Piglib.PIGLIN_LOVED_NUGGETS)) cir.setReturnValue(bl);
	}
}
