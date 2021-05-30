/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ClientMixin
# Created by constantin at 20:51, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.manager.ConfigManager;
import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.external.FastUse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	private int itemUseCooldown;

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		FastUse fuse = (FastUse) ModuleRegistry.search(FastUse.class);
		if (!fuse.isEnabled())
			return;
		if (fuse.fastuse.isEnabled())
			this.itemUseCooldown = 0;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void createClient(RunArgs args, CallbackInfo ci) {
		Cornos.onMinecraftCreate();
	}

	@Inject(method = "stop", at = @At("HEAD"))
	public void sdHook(CallbackInfo ci) {
		ConfigManager.sconf();
	}
}
