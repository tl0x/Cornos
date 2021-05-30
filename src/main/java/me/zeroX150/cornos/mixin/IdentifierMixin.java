/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: IdentifierMixin
# Created by constantin at 23:21, Mär 11 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.Identifier;

@Mixin(Identifier.class)
public class IdentifierMixin {
	@Mutable
	@Shadow
	@Final
	protected String namespace;

	@Mutable
	@Shadow
	@Final
	protected String path;

	@Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
	public void init(String[] id, CallbackInfo ci) {
		String ns = id[0];
		String p = id[1];
		if (ns.equals("minecraft") && p.equals("textures/gui/widgets.png")) {
			this.namespace = "ccl";
			this.path = "widgets.png";
		} else if (ns.equals("minecraft") && p.equals("textures/gui/options_background.png")) {
			this.namespace = "ccl";
			this.path = "transparent.png";
		}
	}
}
