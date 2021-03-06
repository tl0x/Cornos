/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoFall
# Created by constantin at 18:56, Mär 06 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Prevents you from taking fall damage", MType.MOVEMENT);
    }

    @Override
    public void onExecute() {
        // bruh moment #2
        Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket(true));
        super.onExecute();
    }
}
