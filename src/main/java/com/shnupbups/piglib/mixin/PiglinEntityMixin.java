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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;

import com.shnupbups.piglib.Piglib;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin {
	@Inject(method = "equipToOffHand(Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
	public void equipToOffHandInject(ItemStack stack, CallbackInfo ci) {
		if (stack.getItem().isIn(Piglib.PIGLIN_BARTERING_ITEMS)) {
			((MobEntity) (Object) this).equipStack(EquipmentSlot.OFFHAND, stack);
			((MobEntity) (Object) this).updateDropChances(EquipmentSlot.OFFHAND);
			ci.cancel();
		}
	}

	@Redirect(method = "getActivity()Lnet/minecraft/entity/mob/PiglinActivity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinBrain;isGoldenItem(Lnet/minecraft/item/Item;)Z"))
	private boolean shouldAdmireRedirect(Item item) {
		return Piglib.shouldAdmire(item);
	}
}