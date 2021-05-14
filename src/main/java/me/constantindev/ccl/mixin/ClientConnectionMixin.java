package me.constantindev.ccl.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.AntiPacketKick;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    Module aPK;

    @Inject(method = "exceptionCaught", cancellable = true, at = @At("HEAD"))
    public void preventKick(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo ci) {
        if (aPK == null) aPK = ModuleRegistry.search(AntiPacketKick.class);
        if (aPK.isEnabled()) ci.cancel();
    }
}
