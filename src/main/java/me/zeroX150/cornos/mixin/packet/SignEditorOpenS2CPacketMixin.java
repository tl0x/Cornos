/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: SignEditorOpenS2CPacketHook
# Created by constantin at 04:25, Mär 13 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.PacketApplyEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;

@Mixin(SignEditorOpenS2CPacket.class)
public class SignEditorOpenS2CPacketMixin {
	@Inject(method = "apply", at = @At("HEAD"), cancellable = true)
	public void apply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
		boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETHANDLE,
				new PacketApplyEvent((Packet<?>) this, clientPlayPacketListener));
		if (!flag)
			ci.cancel();
	}
}
