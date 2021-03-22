/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: TimeUpdateS2CPacketHook
# Created by constantin at 10:07, Mär 22 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin;

import me.constantindev.ccl.gui.HudElements;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket.class)
public class TimeUpdateS2CPacketHook {
    long lastRecv = 0;
    double calcTps(double n) {
        return (20/Math.max((n-1)/.5,1));
    }
    @Inject(method="apply",at=@At("HEAD"))
    public void received(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        HudElements.tps = calcTps(System.currentTimeMillis()-lastRecv)+" (Last time update: "+(System.currentTimeMillis()-lastRecv)+" ns)";
        lastRecv = System.currentTimeMillis();
    }
}
