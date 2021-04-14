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
public class WorldTimeUpdateS2CPacketMixin {
    boolean calledAlready = false;

    double roundToDecPlace(double i, int n) {
        double mtp = Math.pow(10, n);
        return (Math.round(i * mtp) / mtp);
    }

    double calcTps(double n) {
        return (20.0 / Math.max((n - 1000.0) / (500.0), 1.0));
    }

    @Inject(method = "apply", at = @At("HEAD"))
    public void received(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        if (calledAlready) return;
        calledAlready = true;
        double current = roundToDecPlace(calcTps(System.currentTimeMillis() - HudElements.lastRecv), 2);
        HudElements.minAvg.add(current);
        while (HudElements.minAvg.size() > 10) {
            HudElements.minAvg.subList(0, 1).clear();
        }
        double avg = 0.0;
        for (double d : HudElements.minAvg) {
            avg += d;
        }
        avg /= HudElements.minAvg.size();
        HudElements.tpsAvgHistory.add(avg);
        HudElements.tpsHistory.add(current);
        HudElements.tps = current + ", Last 10 average: " + avg;
        HudElements.lastRecv = System.currentTimeMillis();
    }
}
