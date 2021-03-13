/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoFall
# Created by constantin at 18:56, Mär 06 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class NoFall extends Module {
    Num fallDistance = new Num("fallDistance", 2.9, 10, 0);

    public NoFall() {
        super("NoFall", "Prevents you from taking fall damage", MType.MOVEMENT);
        this.mconf.add(fallDistance);
    }

    @Override
    public void onExecute() {
        // bruh moment #2
        if (Cornos.minecraft.player.fallDistance >= fallDistance.getValue())
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket(true));
        super.onExecute();
    }
}
