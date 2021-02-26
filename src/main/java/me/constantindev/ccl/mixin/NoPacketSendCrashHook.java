package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class NoPacketSendCrashHook {
    @Inject(method = "sendPacket", cancellable = true, at = @At("HEAD"))
    public void overridePacketSend(Packet<?> packet, CallbackInfo ci) {
        Module c = ModuleRegistry.getByName("servercrasher");
        if (c.isOn.isOn() && c.mconf.getByName("mode").value.equals("nprtimeout") && packet instanceof KeepAliveC2SPacket) {
            ClientHelper.sendChat("[Crasher:NPRTimeout] Cancelled keepalive packet");
            ci.cancel();
        }
    }
}
