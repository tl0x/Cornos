/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: EntryListWidgetMixin
# Created by constantin at 10:06, Mär 12 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.mixin.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.EntryListWidget;

@Mixin(EntryListWidget.class)
public class EntryListWidgetMixin {
	@Shadow
	private boolean field_26847;

	@Shadow
	private boolean field_26846;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight,
			CallbackInfo ci) {
		this.field_26847 = false;
		this.field_26846 = false;
	}

	@Inject(method = "method_31323", at = @At("HEAD"), cancellable = true)
	public void override(boolean bl, CallbackInfo ci) {
		this.field_26847 = false;
		this.field_26846 = false;
		ci.cancel();
	}

	@Inject(method = "method_31322", at = @At("HEAD"), cancellable = true)
	public void override1(boolean bl, CallbackInfo ci) {
		this.field_26847 = false;
		this.field_26846 = false;
		ci.cancel();
	}
}
