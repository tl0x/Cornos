/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: GameProfileMixin
# Created by constantin at 21:30, Mär 18 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin;

import com.mojang.authlib.GameProfile;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoginHelloC2SPacket.class)
public abstract class GameProfileMixin {

    @Shadow
    private GameProfile profile;

    @Shadow
    public abstract GameProfile getProfile();

    @Inject(method = "write", cancellable = true, at = @At("HEAD"))
    public void gid(PacketByteBuf buf, CallbackInfo ci) {
        if (ModuleRegistry.getByName("logincrash").isOn.isOn()) {
            buf.writeString(null);
            ci.cancel();
//            this.profile = new GameProfile(null,this.getProfile().getName());
        }
    }
}
