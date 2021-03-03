package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketApplyEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket.class)
public class AntiOffhandCrash {
    @Shadow
    private SoundEvent sound;

    @Inject(method = "apply", cancellable = true, at = @At("HEAD"))
    public void overrideApply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETHANDLE, new PacketApplyEvent((Packet<?>) this, clientPlayPacketListener));
        if (!flag) ci.cancel();
    }
}
