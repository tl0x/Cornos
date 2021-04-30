/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ResourcePackStatusMixin
# Created by constantin at 13:48, Mär 15 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourcePackStatusC2SPacket.class)
public class ResourcePackStatusC2SPacketMixin {
    @Shadow
    private ResourcePackStatusC2SPacket.Status status;

    @Inject(method = "<init>(Lnet/minecraft/network/packet/c2s/play/ResourcePackStatusC2SPacket$Status;)V", at = @At("TAIL"))
    public void init(ResourcePackStatusC2SPacket.Status status, CallbackInfo ci) {
        if (ModuleRegistry.getByName("resourcepackspoof").isEnabled()) {
            this.status = ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED;
        }
    }
}
