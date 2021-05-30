/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: GameProfileMixin
# Created by constantin at 21:30, Mär 18 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.exploit.crash.LoginCrash;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;

@Mixin(LoginHelloC2SPacket.class)
public abstract class LoginHelloC2SPacketMixin {

	@Inject(method = "write", cancellable = true, at = @At("HEAD"))
	public void gid(PacketByteBuf buf, CallbackInfo ci) {
		if (ModuleRegistry.search(LoginCrash.class).isEnabled()) {
			buf.writeString(null);
			ci.cancel();
			// this.profile = new GameProfile(null,this.getProfile().getName());
		}
	}
}
