package me.zeroX150.cornos.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.PacketEvent;
import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.external.AntiPacketKick;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "exceptionCaught", cancellable = true, at = @At("HEAD"))
    public void preventKick(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo ci) {
        if (ModuleRegistry.search(AntiPacketKick.class).isEnabled())
            ci.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", cancellable = true, at = @At("HEAD"))
    public void callEventQueue(Packet<?> packet, CallbackInfo ci) {
        boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETSEND, new PacketEvent(packet));
        if (!flag)
            ci.cancel();
    }
}
