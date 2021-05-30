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

@Mixin(net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket.class)
public class PlaySoundS2CPacketMixin {
	@Inject(method = "apply", cancellable = true, at = @At("HEAD"))
	public void overrideApply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
		boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETHANDLE,
				new PacketApplyEvent((Packet<?>) this, clientPlayPacketListener));
		if (!flag)
			ci.cancel();
	}
}
