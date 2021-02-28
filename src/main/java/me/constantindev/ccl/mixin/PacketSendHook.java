package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class PacketSendHook {
    @Inject(method = "sendPacket", cancellable = true, at = @At("HEAD"))
    public void callEventQueue(Packet<?> packet, CallbackInfo ci) {
        boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETSEND,new PacketEvent(packet));
        if (!flag) ci.cancel();
    }
}
